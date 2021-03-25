package com.xcc.pjtwo.sys.mapper;

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
public interface UserMapper {
    User getById(@Param("id") Long id);
}
