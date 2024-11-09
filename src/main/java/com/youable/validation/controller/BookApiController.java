package com.youable.validation.controller;

import com.youable.validation.dto.request.RegistBookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
//@Validated
public class BookApiController {
    @PostMapping
    public String regist(
            @RequestBody @Valid RegistBookRequest request
    ) {
        return request.toString();
    }

    @GetMapping("/buy")
    public String buy(
            @RequestParam(name = "book_name") @Size(min = 3, max = 20, message = "책의 이름은 3~20 자리로 등록해 주세요.")
            String bookName,
            @RequestParam(name = "amount") @Max(value = 10, message = "한 번에 최대로 구매할 수 있는 수량은 10개 입니다.")
            int amount
    ) {
        return bookName + " " + amount;
    }
}