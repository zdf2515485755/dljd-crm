package com.zdf.dljdweb.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return tuserEntity.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return tuserEntity.getName();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return tuserEntity.getCredentialsNoExpired().equals(UserConstant.USER_STATE_NORMAL);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return tuserEntity.getAccountNoLocked().equals(UserConstant.USER_STATE_NORMAL);
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return tuserEntity.getCredentialsNoExpired().equals(UserConstant.USER_STATE_NORMAL);
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return tuserEntity.getAccountEnabled().equals(UserConstant.USER_STATE_NORMAL);
    }
}
