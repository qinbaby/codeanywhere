package com.xcc.pjtwo.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xcc.pjtwo.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xcc
 * @since 2020-11-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User getById(@Param("id") Long id);
}
