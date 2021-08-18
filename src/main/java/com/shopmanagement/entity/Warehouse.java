package com.shopmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shopmanagement.common.CmnConst;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Warehouse extends BaseEntity{
    private String productId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CmnConst.DATETIME_FORMAT)
    private Date dateOfManufacture;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CmnConst.DATETIME_FORMAT)
    private Date dateOfExpiry;
    private Double price;
    private int amount;
}
