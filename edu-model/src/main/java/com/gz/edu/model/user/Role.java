package com.gz.edu.model.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class Role implements Serializable {
    private Integer id;

    private String roleId;

    private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<Permission> permissions;

}