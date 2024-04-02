package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.MintTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 前端控制器
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Api(value = "MintToken控制器", tags = {"MintToken控制器"})
@RestController
@RequestMapping("/mint-token")
public class MintTokenController {

    @Resource
    private MintTokenService service;

    @ApiOperation("生成mintToken账号")
    @GetMapping
    public ResponseResult getMintToken() {
        return service.getMintToken();
    }

    @ApiOperation("生成mintToken hermes id")
    @GetMapping("/initHermesId")
    public ResponseResult initHermesId() {
        return service.initHermesId();
    }

    @ApiOperation("生成mintToken usd price")
    @GetMapping("/generateUsdPrice")
    public ResponseResult generateUsdPrice() {
        return service.generateUsdPrice();
    }
}
