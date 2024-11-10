package com.youable.validation.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.youable.validation.common.validation.Restriction;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegistUserRequest {
    @Restriction(required = true, patternType = Restriction.PatternType.EMAIL, message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @Restriction(required = true, patternType = Restriction.PatternType.PHONE, message = "유효한 휴대폰 번호 형식이 아닙니다.")
    private String phoneNumber;

    @Restriction(required = true, min = 19, message = "19세 이상 부터 가입 가능합니다.")
    private int age;

    @Restriction(required = true, message = "이름은 필수 값입니다.")
    private String name;

    @Restriction(required = false, patternType = Restriction.PatternType.EMAIL, message = "유효한 이메일 형식이 아닙니다.")
    private String recommendedEmail;
}
