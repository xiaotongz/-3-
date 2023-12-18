package com.zjweu.service.impl;



import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.dto.LoginTO;
import com.zjweu.dto.RegistryFormTO;
import com.zjweu.exception.CommonException;
import com.zjweu.exception.UserException;
import com.zjweu.utils.AppJwtUtil;
import com.zjweu.common.ResponseResult;
import com.zjweu.entity.User;
import com.zjweu.mapper.UserMapper;
import com.zjweu.service.IUserService;
import com.zjweu.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    private RedisUtils redisUtils;


    @Override
    public ResponseResult login(LoginTO login) {
        //1.先取出信息
        String email = login.getEmail();
        String password = login.getPassword();

        //2.先查询是否有该用户，通过email查。如果没用户，则抛出用户密码错误异常
        User user = userMapper.findByEmail(email);
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

    @Override
    public ResponseResult register(RegistryFormTO registryFormTO) {
        //1 先拿到各种数据
        String code = registryFormTO.getCode();
        String email = registryFormTO.getEmail();
        String avatorPath = registryFormTO.getAvatarPath();
        String username = registryFormTO.getUsername();
        String phoneNumber = registryFormTO.getPhoneNumber();
        String password = registryFormTO.getPassword();

        //2 先校验验证码是否正确
        String checkCode = this.redisUtils.get(email);
        boolean check = checkCode != null && checkCode.equals(code);
        if (!this.redisUtils.hasKey(email) || !check) {
            throw new CommonException("邮箱验证码错误或已过期！");
        }

        //3 再查看该邮箱是否已经被注册
        User u = userMapper.findByEmail(email);
        if (u != null ) {
            throw new UserException("注册失败");
        }

        //4 构造user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAvatarPath(avatorPath);
        user.setPhoneNumber(phoneNumber);
        String salt= UUID.randomUUID().toString().replace("-","").substring(0,7);
        user.setSalt(salt);
        String hash = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        user.setPasswordHash(hash);

        //5 插入数据并返回token
        System.out.println(user);
        int insert = userMapper.insertUser(user);
        this.redisUtils.del(email);
        User newUser = userMapper.findByEmail(email);
        String token = AppJwtUtil.getToken(newUser.getUserId().longValue());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        user.setSalt("");
        user.setPasswordHash("");
        map.put("user",user);
        if(insert > 0){
            return ResponseResult.okResult(map);
        }
        else{
            throw new UserException("注册失败");
        }
    }
}
