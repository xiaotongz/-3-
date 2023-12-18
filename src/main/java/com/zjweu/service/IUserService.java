package com.zjweu.service;

import com.zjweu.common.ResponseResult;
import com.zjweu.dto.LoginTO;
import com.zjweu.dto.RegistryFormTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
public interface IUserService {

    public ResponseResult login(LoginTO login);

    ResponseResult register(RegistryFormTO registryFormTO);
}
