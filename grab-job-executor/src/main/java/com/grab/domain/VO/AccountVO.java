package com.grab.domain.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Schema(name = "AccountVO对象", description = "通用返回对象")
public class AccountVO {

    private String id;

    private Integer chainId;

    private String address;

    private LocalDateTime createTs;

    private BigDecimal balance = new BigDecimal(0);

    private List<AccountTokenVO> accountTokenVOList;
}