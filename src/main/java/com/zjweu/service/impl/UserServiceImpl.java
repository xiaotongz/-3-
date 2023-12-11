package com.zjweu.service.impl;


import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.utils.AppJwtUtil;
import com.zjweu.common.ResponseResult;
import com.zjweu.entity.TbUser;
import com.zjweu.mapper.UserMapper;
import com.zjweu.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult login(String email, String password) {
        //1.先判断信息是否为空
        if(StringUtils.isBlank(email) || StringUtils.isBlank(email))
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_MORE_INFO,"信息为空");

        //2.先查询是否有该用户，通过email查。如果没用户，则抛出用户密码错误异常
        TbUser user = userMapper.findByEmail(email);
        if (user == null)
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");

        //3.对用户身份进行检验，使用md5加密（盐+密码）
        String salt = user.getSalt();
        String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if(!pswd.equals(user.getPasswordHash())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        //4.返回token
        String token = AppJwtUtil.getToken(user.getUserId().longValue());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        user.setSalt("");
        user.setPasswordHash("");
        map.put("user",user);
        return ResponseResult.okResult(map);

    }
}
