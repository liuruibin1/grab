package com.grab.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Data
@Accessors(chain = true)
@TableName("t_account")
@Schema(name = "Account对象", description = "通用返回对象")
public class Account {

    @TableField(value = "id")
    private String id;

    @TableField("chain_id")
    private Integer chainId;

    @TableField("address")
    private String address;

    @TableField(value = "create_ts")
    private LocalDateTime createTs;
}