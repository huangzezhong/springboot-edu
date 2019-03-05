package com.gz.edu.model.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Permission implements Serializable {
    private Integer id;

    private String permissionId;

    private String name;

    private String description;

    private String url;

    private String perms;

    private Integer parentId;

    private Integer type;

    private Integer orderNum;

    private String icon;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}