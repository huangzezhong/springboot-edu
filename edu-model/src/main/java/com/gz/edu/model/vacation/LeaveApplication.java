package com.gz.edu.model.vacation;

import com.gz.edu.model.user.User;

import java.io.Serializable;
import java.util.Date;

public class LeaveApplication implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer vaType;

    private String startTime;

    private String endTime;

    private Integer occupyWorkDays;

    private Integer occupyDays;

    private String reason;

    private Integer state;

    private String remark;

    //附加属性
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVaType() {
        return vaType;
    }

    public void setVaType(Integer vaType) {
        this.vaType = vaType;
    }

    public Integer getOccupyWorkDays() {
        return occupyWorkDays;
    }

    public void setOccupyWorkDays(Integer occupyWorkDays) {
        this.occupyWorkDays = occupyWorkDays;
    }

    public Integer getOccupyDays() {
        return occupyDays;
    }

    public void setOccupyDays(Integer occupyDays) {
        this.occupyDays = occupyDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}