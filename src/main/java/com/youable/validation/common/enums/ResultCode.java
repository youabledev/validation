package com.youable.validation.common.enums;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(20000, "성공했습니다."),
    NOT_VALID_DATA(40001, "데이터 검증에 실패했습니다."),
    UNKNOWN(50000, "서버 내부 오류입니다. 관리자에게 문의 바랍니다.");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
