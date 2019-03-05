package com.gz.edu.controller.realm;

import com.gz.edu.model.user.Permission;
import com.gz.edu.model.user.Role;
import com.gz.edu.model.user.User;
import com.gz.edu.service.user.RoleService;
import com.gz.edu.service.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名--对应doGetAuthenticationInfo中new SimpleAuthenticationInfo第一个参数
        User user = (User) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //查询用户名称
        User u = userService.qryUserByUserName(user);
        if (u != null) {
            List<Role> roleList = roleService.qryRoleListByUserId(Integer.parseInt(u.getUserId()));
            if (CollectionUtils.isNotEmpty(roleList)) {
                for (Role role : roleList) {
                    //添加角色
                    simpleAuthorizationInfo.addRole(role.getName());
                    List<Permission> permissions = role.getPermissions();
                    if (CollectionUtils.isNotEmpty(permissions)) {
                        for (Permission permission : permissions) {
                            //添加权限
                            simpleAuthorizationInfo.addStringPermission(permission.getUrl());
                        }
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String userName = authenticationToken.getPrincipal().toString();
        User user = new User(userName);
        User u = userService.qryUserByUserName(user);
        if (u == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    u, //用户名
                    u.getPassword(), //密码
                    ByteSource.Util.bytes(u.getCredentialsSalt()), //salt
                    getName());//realm name --MyShiroRealm
            return simpleAuthenticationInfo;
        }
    }
}
