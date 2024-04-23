package com.zdf.internalcommon.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *@Description Edit user request dto
 *@Author mrzhang
 *@Date 2024/4/22 17:12
 */
@Data
public class EditUserRequestDto{
    /**
     * 主键，自动增长，用户ID
     */
    @NotNull
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */

    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
    **/
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;
}
