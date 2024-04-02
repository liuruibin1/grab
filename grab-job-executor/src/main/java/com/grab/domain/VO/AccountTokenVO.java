package com.grab.domain.VO;

import io.swagger.annotations.Api;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Api(description = "AccountTokenVO对象")
public class AccountTokenVO {

    private Integer chainId;

    private BigDecimal balance;


    private BigDecimal usdPrice;

    private String symbol;

    private Integer decimals;
}