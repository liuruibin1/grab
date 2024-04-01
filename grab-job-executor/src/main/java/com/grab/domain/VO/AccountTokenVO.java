package com.grab.domain.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Schema(name = "AccountTokenVO对象", description = "")
public class AccountTokenVO {

    private Integer chainId;

    private BigDecimal balance;


    private BigDecimal usdPrice;

    private String symbol;

    private Integer decimals;
}