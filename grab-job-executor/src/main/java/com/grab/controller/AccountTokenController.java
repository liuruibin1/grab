package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.AccountTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前端控制器
 *
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Api(value = "AccountTokenApi", tags = {"AccountToken接口"})
@RestController
@RequestMapping("/account-token")
public class AccountTokenController {

    @Resource
    AccountTokenService service;

    @ApiOperation("生成账户余额")
    @GetMapping("/generate")
    public ResponseResult updateAccountBalance() {
        ResponseResult responseResult = service.generate();
        return responseResult;
    }

    @ApiOperation("查询所有账号余额")
    @PostMapping
    public ResponseResult getAccountBalance() {
        ResponseResult responseResult = service.getAccountBalance();
        return responseResult;
    }

    @ApiOperation("导出所有账号余额")
    @PostMapping("/import")
    public ResponseResult importAccountBalance() {
        ResponseResult responseResult = service.importAccountBalance();
        return responseResult;
    }
}
