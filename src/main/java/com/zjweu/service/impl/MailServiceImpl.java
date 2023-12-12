package com.zjweu.service.impl;

import com.zjweu.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * author：Swithin
 * date：2023/12/12 10:10
 **/
@Service
@Slf4j
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 检测邮件信息类
     * @param receiveEmail 接收者
     * @param subject  主题
     * @param emailMsg 内容
     */
    @Override
    public void checkMail(String receiveEmail, String subject, String emailMsg){
        //  StringUtils 需要引入  commons-lang3 依赖
        //  可以用 receiveEmail == null || receiveEmail.isEmpty() 来代替
        if(StringUtils.isEmpty(receiveEmail)) {
            throw new RuntimeException("邮件收件人不能为空");
        }
        if(StringUtils.isEmpty(subject)) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if(StringUtils.isEmpty(emailMsg)) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    /**
     * 发送纯文本邮件
     * @param receiveEmail 接收者
     * @param subject  主题
     * @param emailMsg 内容
     */
    @Override
    public Boolean sendTextMail(String receiveEmail, String subject, String emailMsg) throws MessagingException {
        // 参数检查
        checkMail(receiveEmail, subject, emailMsg);

            // true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            // 邮件发件人
            mimeMessageHelper.setFrom(sendMailer);
            // 邮件收件人
            mimeMessageHelper.setTo(receiveEmail.split(","));
            // 邮件主题
            mimeMessageHelper.setSubject(subject);
            // 邮件内容
            mimeMessageHelper.setText(emailMsg);
            // 邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            // 发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            log.info("发送邮件成功: {} --> {}", sendMailer, receiveEmail);
            return true;

    }
}
