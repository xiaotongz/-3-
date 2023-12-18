package com.zjweu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistryFormTO {
    @NotBlank(message = "昵称不能为空")
    @JsonProperty("username")
    @Size(min = 3, max = 8, message = "昵称字数请限制在3~8个字符之间")
    private String username;
    @NotBlank(message = "邮箱不能为空")
    @JsonProperty("email")
    @Email
    private String email;
    @Length(min = 6, message = "密码长度必须大于6",max = 16)
    @JsonProperty("password")
    private String password;
    @NotBlank(message = "验证码不能为空")
    @JsonProperty("code")
    private String code;
    @JsonProperty("avatarPath")
    private String avatarPath;
    @JsonProperty("phoneNumber")
    private String phoneNumber;


    @JsonProperty("userName")
    public void setUserName(final String userName) {
        this.username = username;
    }

    @JsonProperty("email")
    public void setEmail(final String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public void setPassword(final String password) {
        this.password = password;
    }

    @JsonProperty("code")
    public void setCode(final String code) {
        this.code = code;
    }

    @JsonProperty("avatarPath")
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
