package com.gz.edu.service.user;

import com.gz.edu.model.user.Role;

import java.util.List;

public interface RoleService {

    /**
     * 根据用户ID查找角色列表
     *
     * @param userId 用户ID
     * @return
     */
    public List<Role> qryRoleListByUserId(Integer userId);

}
