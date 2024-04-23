package com.zdf.internalcommon.response;

import com.zdf.internalcommon.entity.TuserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *@Description 类功能简要描述
 *@Author mrzhang
 *@Date 2024/4/22 02:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoResponseDto extends TuserEntity {
    private TuserEntity creator;
    private TuserEntity editor;
}
