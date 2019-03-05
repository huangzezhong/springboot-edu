package com.gz.edu.model.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UserRole implements Serializable {
    private Integer id;

    private String userId;

    private String roleId;

}