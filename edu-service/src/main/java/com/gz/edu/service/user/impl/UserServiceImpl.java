package com.gz.edu.service.user.impl;

import com.gz.edu.dao.user.UserMapper;
import com.gz.edu.model.user.User;
import com.gz.edu.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User qryUserByUserName(User user) {
        if (StringUtils.isNotBlank(user.getUsername())) {
            User u = new User();
            u.setUsername(user.getUsername());
            return userMapper.selectUserByName(user);
        }
        return null;
    }
}
