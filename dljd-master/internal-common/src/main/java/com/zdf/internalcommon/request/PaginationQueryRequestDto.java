package com.zdf.internalcommon.request;

import lombok.Data;

/**
 *@Description Pagination query request dto
 *@Author mrzhang
 *@Date 2024/4/22 16:42
 */
@Data
public class PaginationQueryRequestDto {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
