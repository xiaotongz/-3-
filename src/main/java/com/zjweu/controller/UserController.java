package com.zjweu.controller;


import com.zjweu.common.ResponseResult;
import com.zjweu.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
@RestController
@RequestMapping("/user")
@Tag(name = "UserController",description = "用户的登录注册等请求都在此处理")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseResult login(String email,String passwoed){
        return userService.login(email,passwoed);
    }
}
