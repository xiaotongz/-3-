package com.zjweu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(MessagingException.class)
    public ResponseResult messagingException(MessagingException e){
        log.error("发送邮件失败: " + e.getMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.MAIL_INFO_ERROR,"验证码发送失败");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult runtimeException(RuntimeException e){
        log.error("发送邮件失败: " + e.getMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }



}
