package com.grab.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author robinson
 * @date 2024-03-27
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Accessors(chain = true)
@TableName("t_account_token")
@Api(description = "AccountToken对象")
public class AccountToken {

    @TableField("id")
    private String id;

    @TableField("chain_id")
    private Integer chainId;

    @TableField("account_id")
    private String accountId;

    @TableField("balance")
    private BigDecimal balance;

    @TableField("symbol")
    private String symbol;

    @TableField("decimals")
    private Integer decimals;

    @TableField(value = "create_ts")
    private Date createTs;
}