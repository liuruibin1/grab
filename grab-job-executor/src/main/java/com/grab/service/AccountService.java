package com.grab.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grab.domain.Account;
import com.grab.domain.DTO.ResponseResult;

import java.util.List;

/**
 * 服务类
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
public interface AccountService extends IService<Account> {

    ResponseResult importAccount(Integer chainId, List<String> accountList);

}
