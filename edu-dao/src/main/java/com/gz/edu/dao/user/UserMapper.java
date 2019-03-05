package com.gz.edu.dao.user;

import com.gz.edu.model.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(User record);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(User record);

    User selectUserByName(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}