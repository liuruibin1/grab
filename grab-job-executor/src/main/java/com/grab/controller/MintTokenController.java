package com.grab.controller;

import com.grab.domain.DTO.ResponseResult;
import com.grab.service.AccountService;
import com.grab.service.MintTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
*  前端控制器
* @author robinson
* @date 2024-03-26
* @tags 我爱的人在很远的地方, 我必须更加努力
*/
@Tag(name = "MintToken控制器", description = "MintToken控制器")
@RestController
@RequestMapping("/mint-token")
public class MintTokenController {

    @Resource
    private MintTokenService service;

    @Operation(summary = "generateMintToken账号", description = "生成mintToken账号")
    @GetMapping
    public ResponseResult getMintToken() {
        return service.getMintToken();
    }

    @Operation(summary = "generate hermes id", description = "生成mintToken hermes id")
    @GetMapping("/initHermesId")
    public ResponseResult initHermesId() {
        return service.initHermesId();
    }

    @Operation(summary = "generateMintToken usd price", description = "生成mintToken usd price")
    @GetMapping("/generateUsdPrice")
    public ResponseResult generateUsdPrice() {
        return service.generateUsdPrice();
    }
}
