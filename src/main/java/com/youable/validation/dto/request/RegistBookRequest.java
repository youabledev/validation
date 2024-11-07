package com.youable.validation.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegistBookRequest {
    @Size(min = 1, max = 20, message = "책의 이름은 1~20 자리로 등록해 주세요.")
    private String bookName;

    @NotBlank(message = "카테고리 값은 필수 입니다.")
    private String category;

    @Past(message = "발행일은 과거날짜만 가능합니다. ")
    private LocalDate issuedDay;

    @Max(value = 100, message = "최대로 등록할 수 있는 수량은 100개 입니다.")
    private int amount;
}
