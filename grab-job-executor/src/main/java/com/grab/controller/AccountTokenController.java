package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.AccountTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Tag(name = "AccountTokenApi", description = "AccountToken接口")
@RestController
@RequestMapping("/account-token")
public class AccountTokenController {

    @Resource
    AccountTokenService service;

    @Operation(summary = "生成账户余额", description = "生成账户余额")
    @GetMapping("/generate")
    public ResponseResult updateAccountBalance() {
        ResponseResult responseResult = service.generate();
        return responseResult;
    }

    @Operation(summary = "查询所有账号余额", description = "")
    @PostMapping
    public ResponseResult getAccountBalance() {
        ResponseResult responseResult = service.getAccountBalance();
        return responseResult;
    }

    @Operation(summary = "导出所有账号余额", description = "")
    @PostMapping("/import")
    public ResponseResult importAccountBalance() {
        ResponseResult responseResult = service.importAccountBalance();
        return responseResult;
    }
}
