package com.gz.edu.dao.vacation;

import com.gz.edu.model.vacation.LeaveApplicationDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveApplicationDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LeaveApplicationDetail record);

    int insertSelective(LeaveApplicationDetail record);

    LeaveApplicationDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LeaveApplicationDetail record);

    int updateByPrimaryKey(LeaveApplicationDetail record);
}