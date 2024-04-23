package com.zdf.dljdweb.controller;

import com.github.pagehelper.PageInfo;
import com.zdf.dljdweb.service.impl.TuserServiceImpl;
import com.zdf.internalcommon.request.EditUserRequestDto;
import com.zdf.internalcommon.request.InsertUserRequestDto;
import com.zdf.internalcommon.request.PaginationQueryRequestDto;
import com.zdf.internalcommon.response.GetUserInfoResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 *@Description 类功能简要描述
 *@Author mrzhang
 *@Date 2024/4/21 23:35
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private TuserServiceImpl tuserService;

    @PostMapping("/insertUser")
    public ResponseResult<Integer> insertUser(@Validated @RequestBody InsertUserRequestDto insertUserRequestDto){
        return tuserService.insertUser(insertUserRequestDto);
    }

    @DeleteMapping("/deleteUser")
    public ResponseResult<Integer> deleteUser(@NotBlank @RequestParam Long id){
        return tuserService.deleteUser(id);
    }

    @DeleteMapping("/batchDeleteUser")
    public ResponseResult<Integer> batchDeleteUser(@NotEmpty @RequestParam Long[] ids){
        return tuserService.batchDeleteUser(ids);
    }

    @GetMapping("/getUserInfo")
    public ResponseResult<GetUserInfoResponseDto> getUserInfo(@NotBlank @RequestParam Long id){
        return tuserService.getUserInfo(id);
    }

    @GetMapping("/paginationQuery")
    public ResponseResult<PageInfo> paginationQuery(@RequestBody PaginationQueryRequestDto paginationQueryRequestDto){
        return tuserService.paginationQuery(paginationQueryRequestDto);
    }

    @PutMapping("/editUser")
    public ResponseResult<Integer> editUser(@Validated @RequestBody EditUserRequestDto editUserRequestDto){
        return tuserService.editUser(editUserRequestDto);
    }
}
