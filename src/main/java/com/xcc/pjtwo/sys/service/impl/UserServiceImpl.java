package com.xcc.pjtwo.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xcc.pjtwo.sys.entity.User;
import com.xcc.pjtwo.sys.mapper.UserMapper;
import com.xcc.pjtwo.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xcc
 * @since 2020-11-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getById(Long id) {
        return userMapper.getById(id);
    }
}
