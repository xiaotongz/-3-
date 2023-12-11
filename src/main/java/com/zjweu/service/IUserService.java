package com.zjweu.service;

import com.zjweu.common.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
public interface IUserService {

    public ResponseResult login(String email, String passwoed);

}
