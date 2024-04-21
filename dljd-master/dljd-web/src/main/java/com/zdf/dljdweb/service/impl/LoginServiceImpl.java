package com.zdf.dljdweb.service.impl;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.zdf.dljdweb.security.LogInUserDetail;
import com.zdf.dljdweb.service.LoginService;
import com.zdf.internalcommon.constant.ImageConstant;
import com.zdf.internalcommon.constant.RedisConstant;
import com.zdf.internalcommon.constant.StatusCode;
import com.zdf.internalcommon.request.LoginRequestDto;
import com.zdf.internalcommon.response.GetVerityCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import com.zdf.internalcommon.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *@Description Login service
 *@Author mrzhang
 *@Date 2024/4/20 22:39
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseResult<GetVerityCodeResponseDto> getVerityCode() {
        //生成验证码
        Captcha specCaptcha = new SpecCaptcha(ImageConstant.DEFAULT_WIDTH, ImageConstant.DEFAULT_HEIGHT, ImageConstant.DEFAULT_LENGTH);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String verificationCodeKey = RedisConstant.VERIFY_CODE_KEY_PREFIX + uuid;
        String verificationCode = specCaptcha.text().toLowerCase();
        log.info("uuid: {}", uuid);
        log.info("code: {}", verificationCode);
        // 存入redis
        redisTemplate.opsForValue().set(verificationCodeKey, verificationCode, RedisConstant.VERIFY_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
        GetVerityCodeResponseDto verificationCodeResponseDto = GetVerityCodeResponseDto.builder().uuid(uuid)
                .image(specCaptcha.toBase64())
                .build();

        return ResponseResult.success(verificationCodeResponseDto);
    }

    @Override
    public ResponseResult<String> login(LoginRequestDto loginRequestDto) {
        String uuid = loginRequestDto.getUuid();
        // 判断验证码是否过期
        String verifyCode = (String)redisTemplate.opsForValue().get(RedisConstant.VERIFY_CODE_KEY_PREFIX + uuid);
        if(Objects.isNull(verifyCode)){
            log.error("VerifyCode has expired");
            return ResponseResult.fail(StatusCode.VERIFY_CODE_HAS_EXPIRED.getCode(), StatusCode.VERIFY_CODE_HAS_EXPIRED.getMessage());
        }
        //验证验证码是否正确
        if(!Objects.equals(verifyCode, loginRequestDto.getVerifyCode())){
            log.error("VerifyCode has not match");
            return ResponseResult.fail(StatusCode.VERIFY_CODE_IS_ERROR.getCode(), StatusCode.VERIFY_CODE_IS_ERROR.getMessage());
        }
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        }catch (AuthenticationException e){
            log.error("authentication failed");
            return ResponseResult.fail(StatusCode.AUTHENTICATION_ERROR.getCode(), StatusCode.AUTHENTICATION_ERROR.getMessage());
        }
        LogInUserDetail logInUserDetail = (LogInUserDetail)authentication.getPrincipal();
        String token = JwtUtil.generatorToken(logInUserDetail.getUsername(), logInUserDetail.getPassword());
        redisTemplate.opsForValue().set(RedisConstant.TOKEN_KEY_PREFIX + logInUserDetail.getUsername(), token, RedisConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(RedisConstant.USER_KEY_PREFIX + logInUserDetail.getUsername(), logInUserDetail, RedisConstant.USER_EXPIRE_TIME, TimeUnit.SECONDS);

        return ResponseResult.success(token);
    }
}