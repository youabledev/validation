package com.youable.validation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int code;
    private String msg;

    @Builder
    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
