package com.zdf.dljdweb.filter;

import com.zdf.dljdweb.security.LogInUserDetail;
import com.zdf.internalcommon.constant.RedisConstant;
import com.zdf.internalcommon.constant.StatusCode;
import com.zdf.internalcommon.result.TokenResult;
import com.zdf.internalcommon.util.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *@Description Jwt authentication token filter
 *@Author mrzhang
 *@Date 2024/4/21 18:14
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (Objects.isNull(authorization) || !StringUtils.hasText(authorization.trim())){
            filterChain.doFilter(request, response);
            return ;
        }
        //判断token是否非法
        TokenResult tokenResult = JwtUtil.checkToken(authorization.trim());
        if (Objects.isNull(tokenResult)){
            throw new ServletException(StatusCode.TOKEN_IS_ERROR.getMessage());
        }
        String userName = tokenResult.getUserName();
        String token = (String)redisTemplate.opsForValue().get(RedisConstant.TOKEN_KEY_PREFIX + userName);
        //判断token是否过期
        if (Objects.isNull(token)){
            throw new ServletException(StatusCode.TOKEN_HAS_EXPIRED.getMessage());
        }
        //判断token是否正确
        if (!authorization.equals(token)){
            throw new ServletException(StatusCode.TOKEN_IS_NOT_MATCH.getMessage());
        }
        LogInUserDetail userInfo = (LogInUserDetail) redisTemplate.opsForValue().get(RedisConstant.USER_KEY_PREFIX + userName);
        if (Objects.isNull(userInfo)){
            throw new ServletException(StatusCode.USER_INFO_HAS_EXPIRED.getMessage());
        }
        threadPoolTaskExecutor.execute(()-> {
            redisTemplate.opsForValue().set(RedisConstant.TOKEN_KEY_PREFIX + userName, token, RedisConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(RedisConstant.USER_KEY_PREFIX + userName, userInfo, RedisConstant.USER_EXPIRE_TIME, TimeUnit.SECONDS);
        });
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(userInfo);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}

