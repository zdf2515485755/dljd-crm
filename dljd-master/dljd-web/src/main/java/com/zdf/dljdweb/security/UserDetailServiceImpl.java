package com.zdf.dljdweb.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdf.dljdweb.mapper.TuserMapper;
import com.zdf.internalcommon.entity.TuserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *@Description Retrieve user information from the database
 *@Author mrzhang
 *@Date 2024/4/21 01:01
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource
    private TuserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<TuserEntity> tuserEntityQueryWrapper = new QueryWrapper<>();
        tuserEntityQueryWrapper.eq("login_act", username);
        TuserEntity tuserEntity = userMapper.selectOne(tuserEntityQueryWrapper);
        if (tuserEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new LogInUserDetail(tuserEntity);
    }
}
