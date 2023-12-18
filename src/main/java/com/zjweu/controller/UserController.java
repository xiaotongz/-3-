package com.zjweu.controller;


import com.zjweu.common.ResponseResult;
import com.zjweu.dto.LoginTO;
import com.zjweu.dto.RegistryFormTO;
import com.zjweu.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
@Slf4j
@Tag(name = "UserController",description = "用户的登录注册等请求都在此处理")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseResult login(@Valid @RequestBody LoginTO login){
        log.info("登录{}",login );
        return userService.login(login);
    }

    @PostMapping({"/register"})
    public ResponseResult registry(@Valid @RequestBody RegistryFormTO registryFormTO) {
        log.info("注册{}", registryFormTO);
        return userService.register(registryFormTO);
    }
}
