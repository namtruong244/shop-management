package com.shopmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity{
    private String categoryId;
    private String name;
    private String unit;
    private double wholesalePrice;
    private double retailPrice;
    private String description;
}
