package com.grab.domain.VO;

import io.swagger.annotations.Api;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Api(description = "AccountVO对象")
public class AccountVO {

    private String id;

    private Integer chainId;

    private String address;

    private Date createTs;

    private BigDecimal balance = new BigDecimal(0);

    private List<AccountTokenVO> accountTokenVOList;
}