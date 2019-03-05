package com.gz.edu.model.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RolePermission implements Serializable {
    private Integer id;

    private String roleId;

    private String permissionId;

    public Integer getId() {
        return id;
    }

}