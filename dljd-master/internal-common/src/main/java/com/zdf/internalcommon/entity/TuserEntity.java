package com.zdf.internalcommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @author mrzhang
 * @TableName t_user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TuserEntity extends BaseEntity implements Serializable {
    /**
     * 主键，自动增长，用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 登录账号
     */
    @NotBlank(message = "account can not be empty")
    @Length(min = 1, max = 8, message = "Length between 1 and 8")
    private String loginAct;

    /**
     * 登录密码
     */
    @NotBlank(message = "password can not be empty")
    @Length(min = 1, max = 8, message = "Length between 1 and 8")
    private String loginPwd;

    /**
     * 用户姓名
     */
    @NotBlank(message = "username can not be empty")
    @Length(min = 1, max = 8, message = "Length between 1 and 8")
    private String name;

    /**
     * 用户手机
     */
    @NotBlank(message = "The phone number cannot be empty")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$", message = "The phone number format should be correct")
    private String phone;

    /**
     * 用户邮箱
     */
    @Email(message = "email format is wrong")
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

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}