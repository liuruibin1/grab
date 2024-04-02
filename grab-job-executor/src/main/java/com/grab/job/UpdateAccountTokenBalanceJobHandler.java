package com.grab.job;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grab.domain.Account;
import com.grab.domain.AccountToken;
import com.grab.domain.enumer.RpcEndpoint;
import com.grab.service.AccountService;
import com.grab.service.AccountTokenService;
import com.grab.util.RpcClientProxy;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class UpdateAccountTokenBalanceJobHandler extends BaseJob {

    @Autowired
    AccountTokenService accountTokenService;

    @Autowired
    AccountService accountService;

    /**
     * 定时账户余额usd
     */
    @XxlJob("UpdateAccountTokenBalanceJobHandler:handle")
    public void handle() {
        int MAX_CONCURRENT_REQUESTS = 10; // 设置最大并发请求数量
        int BATCH_SIZE = 20; // 设置每个批次的大小
        int BATCH_INTERVAL_MS = 100; // 设置每个批次之间的间隔时间，单位为毫秒
        ExecutorService executor = Executors.newFixedThreadPool(MAX_CONCURRENT_REQUESTS);
        RpcEndpoint randomEndpoint = RpcEndpoint.getRandomEndpoint();
        RpcClientProxy client = new RpcClientProxy(httpProxyEnable, randomEndpoint.getUrl());
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
                    Runnable task = accountTokenService.createBatchTask(client, batchList, accountTokens, BATCH_SIZE);
                    executor.execute(task);
                    batchList = new ArrayList<>();
                    Thread.sleep(BATCH_INTERVAL_MS); // 控制批次之间的间隔时间
                }
            }

            // 等待所有批次执行完毕
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            int effectRaw = accountTokenService.deleteAll();

            effectRaw += accountTokenService.batchDeleteAndInsert(accountTokens);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown(); // 关闭线程池
        }
    }

}
