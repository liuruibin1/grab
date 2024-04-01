package com.grab.service;

import com.grab.domain.DTO.ResponseResult;
import com.grab.domain.MintToken;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.net.URISyntaxException;

/**
*
*  服务类
* @author robinson
* @date 2024-03-26
* @tags 我爱的人在很远的地方, 我必须更加努力
*/
public interface MintTokenService extends IService<MintToken> {

    ResponseResult getMintToken();

    ResponseResult generateUsdPrice();

    ResponseResult initHermesId();
}
