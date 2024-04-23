package com.zdf.internalcommon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mrzhang
 * @description Status code
 * @date 2024/3/16 19:43
 */
@Getter
@AllArgsConstructor
public enum StatusCode {
    //成功
    SUCCESS(1, "SUCCESS"),
    FAIL(0, "FAIL"),
    TOKEN_IS_EMPTY(10001, "TOKEN IS EMPTY"),
    INSERT_DATA_ERROR(10002, "INSERT DATA ERROR"),
    USER_IS_NOT_EXIT(10003, "USER IS NOT EXIT"),
    UPDATE_COMMUNITY_ERROR(10004, "UPDATE COMMUNITY ERROR"),
    DELETE_COMMUNITY_ERROR(10005, "DELETE COMMUNITY ERROR"),
    COMMUNITY_NUMBER_IS_EMPTY(10006, "COMMUNITY Id IS EMPTY"),
    DEL_FLAG_FORMAT_IS_ERROR(10007, "DEL FLAG FORMAT IS ERROR"),
    DEPT_TABLE_IS_EMPTY(10008, "DEPT TABLE IS EMPTY"),
    VERIFY_CODE_HAS_EXPIRED(10009, "VERIFY_CODE_HAS_EXPIRED"),
    VERIFY_CODE_IS_ERROR(10010, "VERIFY CODE IS ERROR"),
    AUTHENTICATION_ERROR(10011, "AUTHENTICATION ERROR"),
    TOKEN_IS_ERROR(10012, "TOKEN IS ERROR"),
    TOKEN_IS_NOT_MATCH(10013, "TOKEN IS NOT MATCH"),
    TOKEN_HAS_EXPIRED(10014, "TOKEN HAS EXPIRED"),
    USER_INFO_HAS_EXPIRED(10015, "USERINFO HAS EXPIRED"),
    USER_IS_EXIST(10016, "USER IS EXIST");

    private final int code;
    private final String message;
}