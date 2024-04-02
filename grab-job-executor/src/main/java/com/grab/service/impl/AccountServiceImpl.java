package com.grab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grab.domain.Account;
import com.grab.domain.DTO.ResponseResult;
import com.grab.mapper.AccountMapper;
import com.grab.service.AccountService;
import com.xxl.job.core.util.UUIDUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Resource
    AccountMapper mapper;

    @Override
    public ResponseResult importAccount(Integer chainId, List<String> accountList) {
        //循环查询
        List<Account> accounts = new ArrayList<>();
        for (String accountAddress : accountList) {
            Account account = new Account();
            account.setId(UUIDUtil.generateLowerCaseBySeedArray(chainId.toString(), accountAddress));
            account.setAddress(accountAddress);
            account.setChainId(chainId);
            accounts.add(account);
        }
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNotNull(Account::getId);
        remove(queryWrapper);
        int effectRaw = batchDeleteAndInsert(accounts);
        if (effectRaw > 0) {
            return ResponseResult.success();
        }
        return ResponseResult.error("import error");
    }


    public int batchDeleteAndInsert(List<Account> entities) {
        int effectRow = 0;
        try {
            //筛选唯一ID
            List<String> distinctIdList = entities.stream().map(Account::getId).distinct().collect(Collectors.toList());
            //删除库里的
            effectRow += this.mapper.deleteBatchIds(distinctIdList);
            //去除重复的
            List<Account> newList = new ArrayList<>();
            //防重复
            for (Account entity : entities) {
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
}
