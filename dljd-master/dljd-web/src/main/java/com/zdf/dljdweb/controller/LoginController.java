package com.zdf.dljdweb.controller;

import com.zdf.dljdweb.service.LoginService;
import com.zdf.internalcommon.request.LoginRequestDto;
import com.zdf.internalcommon.response.GetVerityCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *@Description Login api
 *@Author mrzhang
 *@Date 2024/4/20 22:29
 */
@RestController
@RequestMapping("/sys")
public class LoginController {
    @Resource
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseResult<String> login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }

    @GetMapping("/getVerityCode")
    public ResponseResult<GetVerityCodeResponseDto> getVerityCode() {
        return loginService.getVerityCode();
    }
}
