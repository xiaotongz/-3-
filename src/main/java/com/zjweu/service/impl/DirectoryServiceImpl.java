package com.zjweu.service.impl;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import com.zjweu.dto.DirectoryTO;
import com.zjweu.mapper.DirectoryMapper;
import com.zjweu.service.IDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * author：Swithin
 * date：2023/12/22 10:49
 **/
@Service
public class DirectoryServiceImpl implements IDirectoryService {
    @Autowired
    private DirectoryMapper directoryMapper;

    @Override
    public ResponseResult addDirectory(DirectoryTO query) {
        //1 如果当前目录为空的话，父目录就是根目录
        if(query.getDid() == null){
            int rootDid = directoryMapper.findRootDirectory(query.getUserId());
            query.setDid(rootDid);
        }

        //2 如果目录的uid不是当前的uid，那就说明这是非法操作了
        //先不写了，有时间再补上这个逻辑

        //3 查询该目录是否已经存在，如果存在，就返回错误
        System.out.println(query.getName());
        Integer num = directoryMapper.findRootDirectoryByName(query.getUserId(), query.getName());
        if(num != null)return ResponseResult.errorResult(AppHttpCodeEnum.DIRECTORY_ERROR,"该目录名字已存在");

        //4 插入数据
        num = directoryMapper.addDirectory(query);
        if(num > 0)return ResponseResult.okResult(200, "创建目录成功");
        else return ResponseResult.errorResult(AppHttpCodeEnum.DIRECTORY_ERROR,"创建目录失败");
    }

    @Override
    public ResponseResult renameDirectory(DirectoryTO query) {
        //1 如果名字没有修改，就行改显示名字相同
        //先不写了，有时间再补上这个逻辑

        //2 设置一些时间
        LocalDateTime modifyTime = LocalDateTime.now();

        //3 更新数据
        Integer num = directoryMapper.renameDirectory(query,modifyTime);
        if(num == 1)return ResponseResult.okResult(200, "重命名目录成功");
        else return ResponseResult.errorResult(AppHttpCodeEnum.DIRECTORY_ERROR,"重命名目录失败");
    }

    @Override
    public ResponseResult deleteDirectory(DirectoryTO query) {
        //1 设置修改时间
        LocalDateTime modifyTime = LocalDateTime.now();

        //2 更新数据
        Integer num = directoryMapper.deleteDirectory(query,modifyTime);
        if(num == 1)return ResponseResult.okResult(200, "删除目录成功");
        else return ResponseResult.errorResult(AppHttpCodeEnum.DIRECTORY_ERROR,"删除目录失败");
    }

    @Override
    public ResponseResult recoverDirectory(DirectoryTO query) {
        //1 设置修改时间
        LocalDateTime modifyTime = LocalDateTime.now();

        //2 更新数据
        Integer num = directoryMapper.deleteDirectory(query,modifyTime);
        if(num == 1)return ResponseResult.okResult(200, "删除目录成功");
        else return ResponseResult.errorResult(AppHttpCodeEnum.DIRECTORY_ERROR,"删除目录失败");
    }
}
