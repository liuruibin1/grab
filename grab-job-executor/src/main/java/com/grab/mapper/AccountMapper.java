package com.grab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grab.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Mapper 接口
 *
 * @author robinson
 * @date 2024-03-26
 * @tags 我爱的人在很远的地方, 我必须更加努力
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
