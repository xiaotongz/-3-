package com.zjweu.controller;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import com.zjweu.entity.ToMail;
import com.zjweu.service.IMailService;
import com.zjweu.utils.VerCodeGenerateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/sendEmail")
    public ResponseResult sendEmail(@RequestBody ToMail toEmail) throws MessagingException {
        System.out.println(toEmail);
        if (toEmail == null || toEmail.getTos() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR, "邮箱信息缺失");
        }
        toEmail.setSubject("网盘验证码");
        // 获取验证码
        String verCode = VerCodeGenerateUtil.generateVerCode();
        log.info("验证码是{}",verCode);
        String content = "用户,您好:\n"
                + "\n本次请求的邮件验证码为:" + verCode + ",本验证码 5 分钟内效，请及时输入。（请勿泄露此验证码）\n"
                + "\n如非本人操作，请忽略该邮件。\n(这是一封通过自动发送的邮件，请不要直接回复）";
        toEmail.setContent(content);

        Boolean check = mailService.sendTextMail(toEmail.getTos(), toEmail.getSubject(), toEmail.getContent());
        if (check) {
            return ResponseResult.okResult(200,"验证码发送成功");
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR, "验证码发送失败");
        }
    }

}
