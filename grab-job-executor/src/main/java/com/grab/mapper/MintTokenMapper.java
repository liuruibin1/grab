package com.grab.mapper;

import com.grab.domain.MintToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
*
*  Mapper 接口
* @author robinson
* @date 2024-03-26
* @tags 我爱的人在很远的地方, 我必须更加努力
*/
@Mapper
public interface MintTokenMapper extends BaseMapper<MintToken> {

    int updatePriceById(@Param("price") BigDecimal price, @Param("id")String id);
}
