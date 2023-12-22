package com.zjweu.common;

public enum AppHttpCodeEnum {

    // 成功段固定为200
    SUCCESS(200,"操作成功"),
    NEED_MORE_INFO(10,"信息缺失"),
    LOGIN_PASSWORD_ERROR(11,"密码错误"),
    NOT_LOGIN(12,"未登录"),
    // TOKEN50~100
    TOKEN_INVALID(13,"无效的TOKEN"),
    CMOMON_ERROE(14,"基础错误"),
    AVATOR_ERROR(15,"头像上传下载失败"),
    REGESTER_EERROR(16,"注册失败"),
    MAIL_INFO_ERROR(17,"邮箱问题"),
    CODE_ERROR(18,"验证码失效或错误"),
    DIRECTORY_ERROR(19,"有关目录问题失败"),
    FILE_ERROE(20,"有关文件问题"),
    PERMISSION_DENY(20,"权限不足"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500,"缺少参数"),
    PARAM_INVALID(501,"无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502,"图片格式有误"),
    SERVER_ERROR(503,"服务器内部错误"),
    // 数据错误 1000~2000
    DATA_EXIST(1000,"数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001,"ApUser数据不存在"),
    DATA_NOT_EXIST(1002,"数据不存在"),
    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000,"无权限操作"),
    NEED_ADMIND(3001,"需要管理员权限");



    int code;
    String errorMessage;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
