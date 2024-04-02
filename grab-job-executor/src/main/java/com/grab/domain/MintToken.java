package com.grab.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxl.job.core.util.UUIDUtil;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Accessors(chain = true)
@TableName("t_mint_token")
@Api(description = "MintToken对象")
public class MintToken {

    @TableField(value = "id")
    private String id;

    @TableField("chain_id")
    private Integer chainId;

    @TableField("address")
    private String address;

    @TableField("symbol")
    private String symbol;

    @TableField("decimals")
    private Integer decimals;

    @TableField("price")
    private BigDecimal price;

    @TableField("logo_uri")
    private String logoUri;

    @TableField(value = "create_ts")
    private Date createTs;

    @TableField("hermes_id")
    private String hermesId;

    public MintToken() {

    }

    public MintToken(Integer chainId, String address, String symbol, Integer decimals) {
        setId(UUIDUtil.generateLowerCaseBySeedArray(chainId.toString(), address, symbol));
        setAddress(address);
        setChainId(chainId);
        setSymbol(symbol);
        setDecimals(decimals);
        setCreateTs(new Date());
    }
}