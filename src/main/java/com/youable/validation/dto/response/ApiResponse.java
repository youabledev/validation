package com.youable.validation.dto.response;

import com.youable.validation.common.enums.ResultCode;

public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ApiResponse(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
