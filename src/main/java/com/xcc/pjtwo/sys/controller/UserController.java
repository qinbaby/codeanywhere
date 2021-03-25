package com.xcc.pjtwo.sys.controller;


import com.xcc.pjtwo.sys.annotation.RoutingDataSource;
import com.xcc.pjtwo.sys.entity.User;
import com.xcc.pjtwo.sys.enums.DataSourceType;
import com.xcc.pjtwo.sys.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xcc
 * @since 2020-11-26
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @RoutingDataSource(DataSourceType.SLAVE)
    @GetMapping("/{id}")
    @ApiOperation("用户信息")
    public User getById(@PathVariable("id") String id){
        return userService.getById(Long.valueOf(id));
    }
}
