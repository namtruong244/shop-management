package com.shopmanagement.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity{
    private String name;
}
