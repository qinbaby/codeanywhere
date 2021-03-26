package com.xcc.pjtwo.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xcc.pjtwo.sys.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcc
 * @since 2020-11-26
 */
public interface IUserService extends IService<User> {
    User getById(Long id);
}
