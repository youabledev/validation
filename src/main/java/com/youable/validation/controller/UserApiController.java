package com.youable.validation.controller;

import com.youable.validation.dto.request.RegistUserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {
    @PostMapping("/regist")
    public String regist(
            @RequestBody @Valid RegistUserRequest request
    ) {
        return request.toString();
    }
}
