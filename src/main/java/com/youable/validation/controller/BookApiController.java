package com.youable.validation.controller;

import com.youable.validation.dto.request.RegistBookRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookApiController {
    @PostMapping
    public String regist(
            @RequestBody @Valid RegistBookRequest request
    ) {
        return request.toString();
    }
}
