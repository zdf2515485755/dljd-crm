package com.zdf.dljdweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.zdf.dljdweb.mapper.TuserMapper;
import com.zdf.dljdweb.service.TuserService;
import com.zdf.internalcommon.constant.StatusCode;
import com.zdf.internalcommon.constant.UserConstant;
import com.zdf.internalcommon.entity.TuserEntity;
import com.zdf.internalcommon.request.EditUserRequestDto;
import com.zdf.internalcommon.request.InsertUserRequestDto;
import com.zdf.internalcommon.request.PaginationQueryRequestDto;
import com.zdf.internalcommon.response.GetUserInfoResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import com.zdf.internalcommon.result.TokenResult;
import com.zdf.internalcommon.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
* @author mrzhang
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2024-04-21 22:15:30
*/
@Service
@Slf4j
public class TuserServiceImpl extends ServiceImpl<TuserMapper, TuserEntity>
    implements TuserService {
    @Resource
    private TuserMapper tuserMapper;
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public ResponseResult<Integer> insertUser(InsertUserRequestDto insertUserRequestDto) {
        QueryWrapper<TuserEntity> tuserEntityQueryWrapper = new QueryWrapper<>();
        tuserEntityQueryWrapper.eq("email", insertUserRequestDto.getEmail())
                .or()
                .eq("login_act", insertUserRequestDto.getLoginAct())
                .or()
                .eq("phone", insertUserRequestDto.getPhone());
        List<TuserEntity> tuserEntities = tuserMapper.selectList(tuserEntityQueryWrapper);
        if (!tuserEntities.isEmpty()) {
            return ResponseResult.fail(StatusCode.USER_IS_EXIST.getCode(), StatusCode.USER_IS_EXIST.getMessage(), tuserEntities.size());
        }
        TuserEntity tuserEntity = new TuserEntity();
        BeanUtils.copyProperties(insertUserRequestDto, tuserEntity);
        String authorization = httpServletRequest.getHeader("Authorization");
        tuserEntity.setCreateBy(getUserId(authorization));
        tuserEntity.setEditBy(getUserId(authorization));

        if (insertUserRequestDto.getLoginPwd() != null) {
            tuserEntity.setLoginPwd(DigestUtils.md5Hex(insertUserRequestDto.getLoginPwd()));
        }
        int count = tuserMapper.insert(tuserEntity);
        return count > 0 ? ResponseResult.success(count) : ResponseResult.fail(count);
    }

    @Override
    public ResponseResult<Integer> deleteUser(Long id) {
        TuserEntity tuserEntity = tuserMapper.selectById(id);
        if (Objects.isNull(tuserEntity)){
            return ResponseResult.fail(StatusCode.USER_IS_NOT_EXIT.getCode(), StatusCode.USER_IS_NOT_EXIT.getMessage(), UserConstant.ZERO);
        }
        int count = tuserMapper.deleteById(id);
        return count > 0 ? ResponseResult.success(count) : ResponseResult.fail(count);
    }

    @Override
    public ResponseResult<Integer> batchDeleteUser(Long[] userIdArray) {
        QueryWrapper<TuserEntity> tuserEntityQueryWrapper = new QueryWrapper<TuserEntity>().in("id", (Object) userIdArray);
        List<TuserEntity> tuserEntities = tuserMapper.selectList(tuserEntityQueryWrapper);
        if (tuserEntities.isEmpty() || tuserEntities.size() == userIdArray.length) {
            return ResponseResult.fail(StatusCode.USER_IS_NOT_EXIT.getCode(), StatusCode.USER_IS_NOT_EXIT.getMessage(), UserConstant.ZERO);
        }
        int count = tuserMapper.deleteBatchIds(Arrays.asList(userIdArray));
        return userIdArray.length == count ? ResponseResult.success(count) : ResponseResult.fail(count);
    }

    @Override
    public ResponseResult<GetUserInfoResponseDto> getUserInfo(Long id) {
        GetUserInfoResponseDto userInfo = tuserMapper.getUserInfo(id);
        log.info("userInfo:{}", userInfo);
        if (Objects.isNull(userInfo)){
            return ResponseResult.fail(StatusCode.USER_IS_NOT_EXIT.getCode(), StatusCode.USER_IS_NOT_EXIT.getMessage());
        }
        return ResponseResult.success(userInfo);
    }

    @Override
    public ResponseResult<PageInfo> paginationQuery(PaginationQueryRequestDto paginationQueryRequestDto) {
        PageMethod.startPage(paginationQueryRequestDto.getPageNo(), paginationQueryRequestDto.getPageSize());
        QueryWrapper<TuserEntity> tuserEntityQueryWrapper = new QueryWrapper<>();
        List<TuserEntity> tuserEntities = tuserMapper.selectList(tuserEntityQueryWrapper);

        return ResponseResult.success(PageInfo.of(tuserEntities));
    }

    @Override
    public ResponseResult<Integer> editUser(EditUserRequestDto editUserRequestDto) {
        UpdateWrapper<TuserEntity> tuserEntityUpdateWrapper = new UpdateWrapper<>();
        String authorization = httpServletRequest.getHeader("Authorization");
        tuserEntityUpdateWrapper.set(editUserRequestDto.getLoginAct() != null && StringUtils.hasText(editUserRequestDto.getLoginAct().trim()), "login_act", editUserRequestDto.getLoginAct())
                .set(editUserRequestDto.getPhone() != null && StringUtils.hasText(editUserRequestDto.getPhone().trim()), "phone", editUserRequestDto.getPhone())
                .set(editUserRequestDto.getEmail() != null && StringUtils.hasText(editUserRequestDto.getEmail().trim()), "email", editUserRequestDto.getEmail())
                .set(editUserRequestDto.getName() != null && StringUtils.hasText(editUserRequestDto.getName().trim()), "name", editUserRequestDto.getName())
                .set(editUserRequestDto.getCredentialsNoExpired() != null, "credentials_no_expired", editUserRequestDto.getCredentialsNoExpired())
                .set(editUserRequestDto.getAccountEnabled() != null, "account_enabled", editUserRequestDto.getAccountEnabled())
                .set(editUserRequestDto.getAccountNoExpired() != null, "account_no_expired", editUserRequestDto.getAccountNoExpired())
                .set(editUserRequestDto.getAccountNoLocked() != null, "account_no_locked", editUserRequestDto.getAccountNoLocked())
                .set(editUserRequestDto.getLoginPwd() != null && StringUtils.hasText(editUserRequestDto.getLoginPwd().trim()), "login_pwd", DigestUtils.md5Hex(editUserRequestDto.getLoginPwd()))
                .set("edit_by", getUserId(authorization))
                .eq("id", editUserRequestDto.getId());
        int count = tuserMapper.update(tuserEntityUpdateWrapper);

        return count > 0 ? ResponseResult.success(count) : ResponseResult.fail(count);
    }

    private Integer getUserId(String token){
        TokenResult tokenResult = JwtUtil.checkToken(token.trim());
        String userName = tokenResult.getUserName();
        QueryWrapper<TuserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", userName);
        TuserEntity user = tuserMapper.selectOne(queryWrapper);
        return user.getId();
    }
}