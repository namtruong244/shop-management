package com.shopmanagement.model;

import com.shopmanagement.entity.User;
import lombok.Data;

@Data
public class UserModel {
    private String id;
    private String username;
    private String password;
    private String fullName;
    private RoleModel role;

    public UserModel(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
        this.role = new RoleModel(user.getRole());
    }
}
