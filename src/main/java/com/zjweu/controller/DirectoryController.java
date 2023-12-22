package com.zjweu.controller;

import com.zjweu.common.BaseContext;
import com.zjweu.common.ResponseResult;
import com.zjweu.dto.DirectoryTO;
import com.zjweu.service.IDirectoryService;
import com.zjweu.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author：Swithin
 * date：2023/12/22 10:24
 **/
@RestController
@RequestMapping("/directory")
public class DirectoryController {
    @Autowired
    private IFileService fileService;
    @Autowired
    private IDirectoryService directoryService;

    @PostMapping("/loadDirectariesAndFiles")
    public ResponseResult loadDirectariesAndFiles(@Validated DirectoryTO query,
                                            String category){
        //2 如果没有指定目录,则按主目录查起
        if(query.getDid() == null){
            int rootPid = fileService.findRootPid(query.getUserId());
            query.setDid(rootPid);
        }
        //3 查询该目录下的所有文件

        return null;
    }

    @PostMapping("/addDirectory")
    public ResponseResult addDirectory(@RequestBody DirectoryTO query){
        //1 先获取用户ID，看和线程是否匹配，如果不匹配就无权操作
        Long currentId = BaseContext.getCurrentId();
        query.setUserId(Math.toIntExact(currentId));
        //2 新建该目录
        ResponseResult res = directoryService.addDirectory(query);
        return res;
    }

    @PostMapping("/renameDirectory")
    public ResponseResult renameDirectory(@RequestBody DirectoryTO query){
        //1 先获取用户ID，看和线程是否匹配，如果不匹配就无权操作
        //在查询的时候会加一个限制where uid = #{currentId}
        //这样子就可以防御那些想更改别的记录的name
        Long currentId = BaseContext.getCurrentId();
        query.setUserId(Math.toIntExact(currentId));
        //2 新建该目录
        ResponseResult res = directoryService.renameDirectory(query);
        return res;
    }

    @PostMapping("/deleteDirectory")
    public ResponseResult deleteDirectory(@RequestBody DirectoryTO query){
        //1 先获取用户ID，看和线程是否匹配，如果不匹配就无权操作
        //在查询的时候会加一个限制where uid = #{currentId}
        //这样子就可以防御那些想更删除别人目录
        Long currentId = BaseContext.getCurrentId();
        query.setUserId(Math.toIntExact(currentId));
        //2 删除该目录
        ResponseResult res = directoryService.deleteDirectory(query);
        return res;
    }

    @PostMapping("/recoverDirectory")
    public ResponseResult recoverDirectory(@RequestBody DirectoryTO query){
        //逻辑和删除是一样的，但是是可以简化成一个方法的
        //现在不想写，有时间再写。
        //1 先获取用户ID，看和线程是否匹配，如果不匹配就无权操作
        //在查询的时候会加一个限制where uid = #{currentId}
        //这样子就可以防御那些想更删除别人目录
        Long currentId = BaseContext.getCurrentId();
        query.setUserId(Math.toIntExact(currentId));
        //2 恢复该目录
        ResponseResult res = directoryService.recoverDirectory(query);
        return res;
    }

    @PostMapping("/deleteDirectoryByList")
    public ResponseResult deleteDirectoryByList(@RequestBody DirectoryTO query){
        //1 先获取用户ID，看和线程是否匹配，如果不匹配就无权操作
        //在查询的时候会加一个限制where uid = #{currentId}
        //这样子就可以防御那些想更删除别人目录
        Long currentId = BaseContext.getCurrentId();
        query.setUserId(Math.toIntExact(currentId));
        //2 根据did删除目录
        // 等有时间再写

        return null;
    }
}
