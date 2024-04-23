package com.zdf.dljdweb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdf.internalcommon.entity.TuserEntity;
import com.zdf.internalcommon.request.EditUserRequestDto;
import com.zdf.internalcommon.request.InsertUserRequestDto;
import com.zdf.internalcommon.request.PaginationQueryRequestDto;
import com.zdf.internalcommon.response.GetUserInfoResponseDto;
import com.zdf.internalcommon.result.ResponseResult;

/**
* @author mrzhang
* @description 针对表【t_user(用户表)】的数据库操作Service
* @createDate 2024-04-21 22:15:30
*/
public interface TuserService extends IService<TuserEntity> {
    /**
     * @param insertUserRequestDto:
     * @return ResponseResult<Integer>
     * @author mrzhang
     * @description Insert user
     * @date 2024/4/22 00:16
     */
    ResponseResult<Integer> insertUser(InsertUserRequestDto insertUserRequestDto);
    /**
     * @param id:
     * @return ResponseResult<Integer>
     * @author mrzhang
     * @description Delete user
     * @date 2024/4/22 01:55
     */
    ResponseResult<Integer> deleteUser(Long id);
    /**
     * @param ids:
     * @return ResponseResult<Integer>
     * @author mrzhang
     * @description Batch delete user
     * @date 2024/4/22 01:55
     */
    ResponseResult<Integer> batchDeleteUser(Long[] ids);
    /**
     * @param id:
     * @return null
     * @author mrzhang
     * @description Get user info
     * @date 2024/4/22 02:13
     */
    ResponseResult<GetUserInfoResponseDto> getUserInfo(Long id);
    /**
     * @param paginationQueryRequestDto:
     * @return ResponseResult<List<TuserEntity>>
     * @author mrzhang
     * @description Pagination query
     * @date 2024/4/22 16:48
     */
    ResponseResult<PageInfo> paginationQuery(PaginationQueryRequestDto paginationQueryRequestDto);
    /**
     * @param editUserRequestDto:
     * @return ResponseResult<Integer>
     * @author mrzhang
     * @description Edit user
     * @date 2024/4/22 17:18
     */
    ResponseResult<Integer> editUser(EditUserRequestDto editUserRequestDto);
}

