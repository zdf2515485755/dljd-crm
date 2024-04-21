package com.zdf.dljdweb.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zdf.internalcommon.result.TokenResult;
import com.zdf.internalcommon.util.JwtUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 *@Description Automatic implementation of database table field assignment
 *@Author mrzhang
 *@Date 2024/4/3 01:33
 */
@Component
public class DataBaseMetaObjectHandler implements MetaObjectHandler {
    private final HttpServletRequest httpServletRequest;

    public DataBaseMetaObjectHandler(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        TokenResult tokenResult = getToken(httpServletRequest);
        if (!Objects.isNull(tokenResult)) {
            this.strictInsertFill(metaObject, "createBy", String.class, tokenResult.getUserName());
            this.strictInsertFill(metaObject, "editBy", String.class, tokenResult.getUserName());
        }
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "editTime", Date.class, new Date());
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        TokenResult tokenResult = getToken(httpServletRequest);
        this.setFieldValByName("editBy", tokenResult.getUserName(), metaObject);
        this.setFieldValByName("editTime", new Date(), metaObject);
    }

    private TokenResult getToken(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        return JwtUtil.checkToken(authorization);
    }
}