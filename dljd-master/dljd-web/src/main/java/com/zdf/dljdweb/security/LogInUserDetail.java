package com.zdf.dljdweb.security;

import com.zdf.internalcommon.constant.UserConstant;
import com.zdf.internalcommon.entity.TuserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 *@Description Encapsulating classes for user information verification
 *@Author mrzhang
 *@Date 2024/4/21 01:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInUserDetail implements UserDetails {
    private TuserEntity tuserEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return tuserEntity.getLoginPwd();
    }

    @Override
    public String getUsername() {
        return tuserEntity.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return tuserEntity.getCredentialsNoExpired().equals(UserConstant.USER_STATE_NORMAL);
    }

    @Override
    public boolean isAccountNonLocked() {
        return tuserEntity.getAccountNoLocked().equals(UserConstant.USER_STATE_NORMAL);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return tuserEntity.getCredentialsNoExpired().equals(UserConstant.USER_STATE_NORMAL);
    }

    @Override
    public boolean isEnabled() {
        return tuserEntity.getAccountEnabled().equals(UserConstant.USER_STATE_NORMAL);
    }
}
