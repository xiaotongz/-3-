package com.zjweu.controller;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import com.zjweu.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * author：Swithin
 * date：2023/12/18 14:49
 * description: This class is used to accept requests like image(avatoe) upload and image download
 **/

@RestController()
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${profile.path}")
    private String path;

    @PostMapping("/uploadAvator")
    @ResponseBody
    public ResponseResult uploadAvator(@RequestParam("myfile") MultipartFile myfile, Model model, HttpSession httpSession) {
        try {
            log.info("上传头像" );
            if(!myfile.isEmpty()) {
                //获得上传文件原名
                String fileName = UUID.randomUUID().toString() + myfile.getOriginalFilename();
                httpSession.setAttribute("fileName",fileName);
                //生成随机名
                File filePath = new File(path  + File.separator + fileName);
                //如果文件目录不存在，创建目录
                if(!filePath.getParentFile().exists()) {
                    filePath.getParentFile().mkdirs();
                }
                //将上传文件保存到一个目标文件中
                myfile.transferTo(filePath);
                return ResponseResult.okResult(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommonException("头像上传失败");
        }
        return null;
    }

    // 下载头像
    @RequestMapping("/downLoadAvator")
    public ResponseResult downLoadAvator(HttpServletRequest request, HttpServletResponse response, Model model, String fileName) {
        try {
            log.info("下载头像{}",fileName );
            if(fileName != "null" || fileName != ""){
                //输入流，通过输入流读取文件内容
                FileInputStream fileInputStream = new FileInputStream(new File(path + File.separator + fileName));
                //输出流，通过输出流将文件写回浏览器
                ServletOutputStream outputStream = response.getOutputStream();
                response.setContentType("image/jpeg");
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len = fileInputStream.read(bytes)) != -1){
                    outputStream.write(bytes,0,len);
                    outputStream.flush();
                }
                //关闭资源
                outputStream.close();
                fileInputStream.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("头像下载失败失败");
        }
        return null;
    }
}
