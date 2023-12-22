package com.zjweu.service.impl;

import com.zjweu.mapper.FileMapper;
import com.zjweu.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author：Swithin
 * date：2023/12/22 10:22
 **/
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int findRootPid(int uid) {
        fileMapper.findRootPid(uid);
        return 0;
    }
}
