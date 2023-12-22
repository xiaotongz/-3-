package com.zjweu.service;

import com.zjweu.common.ResponseResult;
import com.zjweu.dto.DirectoryTO;

public interface IDirectoryService {
    ResponseResult addDirectory(DirectoryTO query);

    ResponseResult renameDirectory(DirectoryTO query);

    ResponseResult deleteDirectory(DirectoryTO query);

    ResponseResult recoverDirectory(DirectoryTO query);
}
