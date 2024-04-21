package com.zdf.internalcommon.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 *@Description Login request dto
 *@Author mrzhang
 *@Date 2024/4/20 23:14
 */
@Data
public class LoginRequestDto {
    @NotBlank(message = "username can not be empty")
    @Length(min = 1, max = 8, message = "The length cannot exceed 8")
    private String username;
    @NotBlank(message = "password can not be empty")
    private String password;
    @NotBlank(message = "uuid can not be empty")
    private String uuid;
    @NotBlank(message = "verifyCode can not be empty")
    private String verifyCode;
}

