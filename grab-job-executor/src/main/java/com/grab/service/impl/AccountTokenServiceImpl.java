package com.grab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grab.domain.Account;
import com.grab.domain.AccountToken;
import com.grab.domain.DTO.ResponseResult;
import com.grab.domain.MintToken;
import com.grab.domain.VO.AccountTokenVO;
import com.grab.domain.VO.AccountVO;
import com.grab.domain.enumer.RpcEndpoint;
import com.grab.mapper.AccountTokenMapper;
import com.grab.mapper.MintTokenMapper;
import com.grab.service.AccountService;
import com.grab.service.AccountTokenService;
import com.grab.util.RpcClientProxy;
import com.grab.util.SolClientUtil;
import com.xxl.job.core.util.BatchesUtil;
import com.xxl.job.core.util.BeanUtil;
import com.xxl.job.core.util.UUIDUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.p2p.solanaj.core.PublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Service
public class AccountTokenServiceImpl extends ServiceImpl<AccountTokenMapper, AccountToken> implements AccountTokenService {

    @Value("${app.http.proxy.enable}")
    public boolean httpProxyEnable;
    @Resource
    AccountTokenMapper mapper;
    @Resource
    MintTokenMapper mintTokenMapper;

    @Resource
    AccountService accountService;

    @Override
    public ResponseResult generate() {
        int MAX_CONCURRENT_REQUESTS = 10; // 设置最大并发请求数量
        int BATCH_SIZE = 20; // 设置每个批次的大小
        int BATCH_INTERVAL_MS = 100; // 设置每个批次之间的间隔时间，单位为毫秒
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CONCURRENT_REQUESTS);
        RpcEndpoint randomEndpoint = RpcEndpoint.getRandomEndpoint();
        RpcClientProxy client = new RpcClientProxy(httpProxyEnable,randomEndpoint.getUrl());
        List<AccountToken> accountTokens = new ArrayList<>();
        try {
            //查询所有账户钱包
            LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.isNotNull(Account::getId);
            List<Account> list = accountService.list(queryWrapper);

            List<Account> batchList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                batchList.add(list.get(i));
                if (batchList.size() == BATCH_SIZE || i == list.size() - 1) {
                    // 创建任务
                    Runnable task = createBatchTask(client, batchList, accountTokens, BATCH_SIZE);
                    executor.execute(task);
                    batchList = new ArrayList<>();
                    Thread.sleep(BATCH_INTERVAL_MS); // 控制批次之间的间隔时间
                }
            }

            // 等待所有批次执行完毕
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            LambdaQueryWrapper<AccountToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.isNotNull(AccountToken::getId);
            this.mapper.delete(lambdaQueryWrapper);

            int effectRaw = batchDeleteAndInsert(accountTokens);
            if (effectRaw > 0) {
                return ResponseResult.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 关闭线程池
        }
        return ResponseResult.error("generate balance error");
    }

    @Override
    public List<AccountToken> getByAccountId(String accountId) {
        LambdaQueryWrapper<AccountToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AccountToken::getAccountId, accountId);
        lambdaQueryWrapper.gt(AccountToken::getBalance, new BigDecimal(0));
        List<AccountToken> accountTokens = this.mapper.selectList(lambdaQueryWrapper);
        return accountTokens;
    }

    @Override
    public ResponseResult getAccountBalance() {
        List<Account> accounts = accountService.list();
        List<AccountVO> accountVOs = BeanUtil.copyToNewList(accounts, AccountVO.class);
        for (AccountVO accountVO : accountVOs) {
            List<AccountToken> accountTokens = getByAccountId(accountVO.getId());
            List<AccountTokenVO> accountTokenVOList = BeanUtil.copyToNewList(accountTokens, AccountTokenVO.class);
            if (CollectionUtils.isNotEmpty(accountTokenVOList)) {
                for (AccountTokenVO accountTokenVO : accountTokenVOList) {
                    //根据符号，chainId，精度查询usd价格
                    LambdaQueryWrapper<MintToken> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MintToken::getChainId, accountTokenVO.getChainId());
                    queryWrapper.eq(MintToken::getSymbol, accountTokenVO.getSymbol());
                    queryWrapper.eq(MintToken::getDecimals, accountTokenVO.getDecimals());
                    MintToken mintToken = this.mintTokenMapper.selectOne(queryWrapper);
                    if (null != mintToken && null != mintToken.getPrice() && null != accountTokenVO.getBalance()) {
                        accountTokenVO.setUsdPrice(mintToken.getPrice().multiply(accountTokenVO.getBalance()));
                    }
                }
            }
            accountVO.setAccountTokenVOList(accountTokenVOList);
        }
        return ResponseResult.success(accountVOs);
    }

    @Override
    public ResponseResult importAccountBalance() {
        List<Account> accounts = accountService.list();
        List<AccountVO> accountVOs = BeanUtil.copyToNewList(accounts, AccountVO.class);
        for (AccountVO accountVO : accountVOs) {
            List<AccountToken> accountTokens = getByAccountId(accountVO.getId());
            List<AccountTokenVO> accountTokenVOList = BeanUtil.copyToNewList(accountTokens, AccountTokenVO.class);
            if (CollectionUtils.isNotEmpty(accountTokenVOList)) {
                for (AccountTokenVO accountTokenVO : accountTokenVOList) {
                    //根据符号，chainId，精度查询usd价格
                    LambdaQueryWrapper<MintToken> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(MintToken::getChainId, accountTokenVO.getChainId());
                    queryWrapper.eq(MintToken::getSymbol, accountTokenVO.getSymbol());
                    queryWrapper.eq(MintToken::getDecimals, accountTokenVO.getDecimals());
                    MintToken mintToken = this.mintTokenMapper.selectOne(queryWrapper);
                    if (null != mintToken && null != mintToken.getPrice() && null != accountTokenVO.getBalance()) {
                        accountVO.setBalance(accountVO.getBalance().add(mintToken.getPrice().multiply(accountTokenVO.getBalance())));
                    }
                }
            }
        }
        String[] headers = {"钱包地址", "usd总余额", "网络id"};
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // 设置标题行
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        // 写入数据
        int rowNum = 1;
        for (AccountVO accountVO : accountVOs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(accountVO.getAddress());
            row.createCell(1).setCellValue(accountVO.getBalance().doubleValue());
            row.createCell(2).setCellValue(accountVO.getChainId().toString());
        }
        // 调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        String filePath = "B:\\download\\account_data.xlsx";
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            return ResponseResult.error(e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException es) {
                return ResponseResult.error(es.getMessage());
            }
        }
        return ResponseResult.success();
    }


    public Runnable createBatchTask(RpcClientProxy clientProxy, List<Account> batchList, List<AccountToken> accountTokens, int BATCH_SIZE) {
        return () -> {
            for (Account account : batchList) {
                try {
                    PublicKey accountPublicKey = new PublicKey(account.getAddress());
                    long balance = SolClientUtil.getBalanceWithRetries(clientProxy, accountPublicKey);
                    BigDecimal solBalance = BigDecimal.valueOf(balance).divide(BigDecimal.TEN.pow(9));
                    AccountToken accountToken = addAccountToken(account, 9, "SOL", solBalance);
                    accountTokens.add(accountToken);

                    // 查询所有mint token并按批次处理
                    LambdaQueryWrapper<MintToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.isNotNull(MintToken::getId);
                    List<MintToken> mintTokens = mintTokenMapper.selectList(lambdaQueryWrapper);

                    // 将mintTokens列表分成批次
                    List<List<MintToken>> batches = BatchesUtil.splitIntoBatches(mintTokens, BATCH_SIZE);

                    // 对每个批次执行请求
                    for (List<MintToken> batch : batches) {
                        List<AccountToken> batchAccountTokens = new ArrayList<>();
                        for (MintToken mintToken : batch) {
                            BigDecimal tokenBalance = SolClientUtil.getTokenBalanceWithRetries(clientProxy, accountPublicKey, mintToken);
                            AccountToken accountTokenOne = addAccountToken(account, mintToken.getDecimals(), mintToken.getSymbol(), tokenBalance);
                            batchAccountTokens.add(accountTokenOne);
                        }
                        // 将批次结果添加到总结果列表
                        accountTokens.addAll(batchAccountTokens);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    public AccountToken addAccountToken(Account account, Integer decimals, String symbol, BigDecimal balance) {
        AccountToken accountToken = new AccountToken();
        accountToken.setId(UUIDUtil.generateLowerCaseBySeedArray(account.getChainId().toString(), account.getId(), symbol, decimals.toString()));
        accountToken.setAccountId(account.getId());
        accountToken.setChainId(account.getChainId());
        accountToken.setDecimals(decimals);
        accountToken.setSymbol(symbol);
        accountToken.setBalance(balance);
        accountToken.setCreateTs(new Date());
        return accountToken;
    }

    @Transactional
    public int batchDeleteAndInsert(List<AccountToken> entities) {
        int effectRow = 0;
        try {
            //筛选唯一ID
            List<String> distinctIdList = entities.stream().map(AccountToken::getId).distinct().collect(Collectors.toList());
            //删除库里的
            effectRow += this.mapper.deleteBatchIds(distinctIdList);
            //去除重复的
            List<AccountToken> newList = new ArrayList<>();
            //防重复
            for (AccountToken entity : entities) {
                boolean exists = newList.stream().anyMatch(i -> i.getId().equals(entity.getId()));
                if (!exists) {
                    newList.add(entity);
                }
            }
            effectRow += this.saveBatch(newList, DEFAULT_BATCH_SIZE) ? newList.size() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return effectRow;
    }


    public int deleteAll() {
        LambdaQueryWrapper<AccountToken> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.isNotNull(AccountToken::getId);
        return this.mapper.delete(lambdaQueryWrapper);
    }
}
