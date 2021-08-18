package com.shopmanagement.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity implements Serializable {
    protected String id;
    protected int deleteFlg = 0;
    protected Date createAt = new Date();
    protected Date updateAt = new Date();
}
