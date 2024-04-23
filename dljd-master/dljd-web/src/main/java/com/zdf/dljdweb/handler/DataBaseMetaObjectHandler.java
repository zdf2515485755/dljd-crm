package com.zdf.dljdweb.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *@Description Automatic implementation of database table field assignment
 *@Author mrzhang
 *@Date 2024/4/3 01:33
 */
@Component
public class DataBaseMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "editTime", Date.class, new Date());
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("editTime", new Date(), metaObject);
    }
}