package com.zdf.dljdweb.service;

import com.zdf.internalcommon.request.LoginRequestDto;
import com.zdf.internalcommon.response.GetVerityCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;

/**
 * @Description Login service
 * @Author mrzhang
 * @Date 2024/4/20 22:33
 */
public interface LoginService {
    /**
     * @return ResponseResult<GetVerityCodeResponseDto>
     * @author mrzhang
     * @description Get verity code
     * @date 2024/4/20 22:46
     */
    ResponseResult<GetVerityCodeResponseDto> getVerityCode();
    /**
     * @return ResponseResult<String>
     * @author mrzhang
     * @description login
     * @date 2024/4/20 22:47
     */
    ResponseResult<String> login(LoginRequestDto loginRequestDto);
}
