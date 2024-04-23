package com.zdf.dljdweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdf.internalcommon.entity.TuserEntity;
import com.zdf.internalcommon.response.GetUserInfoResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author mrzhang
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2024-04-21 22:15:30
* @Entity generator.domain.TUser
*/
@Mapper
public interface TuserMapper extends BaseMapper<TuserEntity> {
    /**
     * @param id:
     * @return GetUserInfoResponseDto
     * @author mrzhang
     * @description Get user info
     * @date 2024/4/23 23:22
     */
    GetUserInfoResponseDto getUserInfo(@Param("id") Long id);
}




