package com.shopmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity{
    private String name;
}
