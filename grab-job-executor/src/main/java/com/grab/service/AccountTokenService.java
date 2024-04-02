package com.grab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grab.domain.Account;
import com.grab.domain.AccountToken;
import com.grab.domain.DTO.ResponseResult;
import com.grab.util.RpcClientProxy;

import java.util.List;

/**
 * 服务类
 *
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
public interface AccountTokenService extends IService<AccountToken> {

    ResponseResult generate();

    List<AccountToken> getByAccountId(String accountId);

    ResponseResult getAccountBalance();

    ResponseResult importAccountBalance();

    int batchDeleteAndInsert(List<AccountToken> entities);

    Runnable createBatchTask(RpcClientProxy clientProxy, List<Account> batchList, List<AccountToken> accountTokens, int BATCH_SIZE);
    int deleteAll();
}
