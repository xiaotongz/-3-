package com.zjweu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @version IntelliJ IDEA || java version 11.
 * @Author: Oliver
 * @Description: 邮箱验证码实体类
 * @Date: 2023-07-02
 * @Time: 14:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor //生成一个包含所有类字段的构造函数
public class MailTO implements Serializable {
    /**
     *  邮件接受方
     */
    @NotNull(message = "邮箱不能为空")
    private String tos;
    /**
     *      邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

}
