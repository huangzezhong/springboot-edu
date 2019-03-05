package com.gz.edu.dao.vacation;

import com.gz.edu.model.vacation.LeaveApplication;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LeaveApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LeaveApplication record);

    int insertSelective(LeaveApplication record);

    LeaveApplication selectByPrimaryKey(Integer id);

    List<LeaveApplication> selectListByUserId(Integer userId);

    int updateByPrimaryKeySelective(LeaveApplication record);

    int updateByPrimaryKey(LeaveApplication record);

    List<LeaveApplication> selectListByIds(Map map);
}