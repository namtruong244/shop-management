package com.shopmanagement.model;

import com.shopmanagement.common.ERole;
import lombok.Data;

@Data
public class RoleModel {
    private ERole name;

    public RoleModel(String role){
        this.name = ERole.valueOf(role);
    }
}
