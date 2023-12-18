package com.zjweu.exception;

import com.zjweu.common.AppHttpCodeEnum;
import com.zjweu.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

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
        return ResponseResult.errorResult(AppHttpCodeEnum.CMOMON_ERROE,e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult runtimeException(RuntimeException e){
        log.error("服务器错误: " + e.getMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseResult codeException(CommonException e){
        log.error("验证码失效或错误: " + e.getMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.CMOMON_ERROE,e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseResult userException(UserException e){
        log.error("用户错误 " + e.getMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.REGESTER_EERROR,e.getMessage());
    }


}
