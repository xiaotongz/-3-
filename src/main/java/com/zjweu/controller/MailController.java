package com.zjweu.controller;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import com.zjweu.dto.MailTO;
import com.zjweu.entity.User;
import com.zjweu.exception.UserException;
import com.zjweu.mapper.UserMapper;
import com.zjweu.service.IMailService;
import com.zjweu.service.IUserService;
import com.zjweu.utils.RedisUtils;
import com.zjweu.utils.VerCodeGenerateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

/**
 * author：Swithin
 * date：2023/12/12 10:13
 **/
@RestController
@RequestMapping("/email")
@Slf4j
public class MailController {

    @Autowired
    private IMailService mailService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;


    @PostMapping("/sendEmail")
    public ResponseResult sendEmail( @Validated @RequestBody MailTO toEmail) throws MessagingException {
        log.info("发送验证码{}",toEmail.getTos());
        if (toEmail == null || toEmail.getTos() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR, "邮箱信息缺失");
        }
        toEmail.setSubject("网盘验证码");
        User u = userMapper.findByEmail(toEmail.getTos());
        if (u != null ) {
            return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR, "邮箱已经注册");
        }
        // 获取验证码
        String verCode = VerCodeGenerateUtil.generateVerCode();
        log.info("验证码是{}",verCode);
        //将验证码存入redis
        redisUtils.set(toEmail.getTos(), verCode);
        String content = "用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + verCode + ",本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）";
        toEmail.setContent(content);
        //发送验证码
        Boolean check = mailService.sendTextMail(toEmail.getTos(), toEmail.getSubject(), toEmail.getContent());
        if (check) {
            return ResponseResult.okResult(200,"验证码发送成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR, "验证码发送失败");
        }
    }

}
