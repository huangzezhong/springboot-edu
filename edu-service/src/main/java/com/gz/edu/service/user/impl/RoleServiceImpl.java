package com.gz.edu.service.user.impl;

import com.gz.edu.dao.user.RoleMapper;
import com.gz.edu.model.user.Role;
import com.gz.edu.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> qryRoleListByUserId(Integer userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }
}
