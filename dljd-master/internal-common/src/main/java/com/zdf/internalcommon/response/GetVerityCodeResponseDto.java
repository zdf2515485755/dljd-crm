package com.zdf.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@Description Get verity code response
 *@Author mrzhang
 *@Date 2024/4/20 22:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVerityCodeResponseDto {
    private String image;
    private String uuid;
}

