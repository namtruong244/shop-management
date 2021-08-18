package com.shopmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO {
    private int total;
    private int pageSize;
    private int currentPage;
}
