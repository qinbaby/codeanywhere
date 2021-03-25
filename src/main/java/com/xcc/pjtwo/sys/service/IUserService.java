package com.xcc.pjtwo.sys.service;

import com.xcc.pjtwo.sys.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcc
 * @since 2020-11-26
 */
public interface IUserService {
    User getById(Long id);
}
