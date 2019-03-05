package com.gz.edu.service.user;


import com.gz.edu.model.user.User;

public interface UserService {

    /**
     * 根据用户名查询用户
     *
     * @param user 用户名
     * @return
     */
    public User qryUserByUserName(User user);

}
