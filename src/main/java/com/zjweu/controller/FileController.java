package com.zjweu.controller;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import com.zjweu.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * author：Swithin
 * date：2023/12/22 14:34
 * description：该类是用来上传文件，秒传文件，修改文件信息等操作的
 **/

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private IFileService fileService;

    @Value("${file.path}")
    private String filePath;

    @PostMapping("/uploadFileSlow")
    public ResponseResult uploadFileSlow(@RequestParam("file") MultipartFile file){
        //1 先对文件进行验证是否为空
        if (file.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_ERROE,"文件是空的");
        }
        //2 重新构造文件的名字,和新路径
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        StringBuilder tempName = new StringBuilder();
        tempName.append(UUID.randomUUID().toString()).append(suffixName);
        String newFileName = tempName.toString();
        File path = new File(filePath  + File.separator + newFileName);

        //3 保存文件
        try {
            System.out.println(path);
            file.transferTo(path);
        }catch (IOException e){
            e.printStackTrace();
            return ResponseResult.errorResult(AppHttpCodeEnum.FILE_ERROE,"文件保存到本地失败");
        }

        return null;
    }
}
