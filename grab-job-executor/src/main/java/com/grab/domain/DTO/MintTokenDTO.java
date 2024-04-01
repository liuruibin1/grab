package com.grab.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Schema(name = "MintTokenDTO对象", description = "MintTokenDTO对象")
public class MintTokenDTO {

    private String id;

    private String hermesId;

    private BigDecimal price;

}