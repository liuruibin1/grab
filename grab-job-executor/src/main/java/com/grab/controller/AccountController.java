package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 前端控制器
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Api(value = "accountControllerApi", tags = {"account接口"})
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService service;


    @ApiOperation("导入账号")
    @PostMapping
    public ResponseResult importAccount(@RequestParam(name = "chainId", required = true) Integer chainId,
                                        @RequestBody List<String> accountList) {
        ResponseResult responseResult = service.importAccount(chainId, accountList);
        return responseResult;
    }
}
