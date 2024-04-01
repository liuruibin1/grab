package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端控制器
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Tag(name = "accountControllerApi", description = "account接口")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService service;


    @Operation(summary = "导入账号", description = "account数组和chainId")
    @PostMapping
    public ResponseResult importAccount(@Parameter(name = "chainId", description = "chainId", required = true) Integer chainId,
                                        @RequestBody List<String> accountList) {
        ResponseResult responseResult = service.importAccount(chainId, accountList);
        return responseResult;
    }
}
