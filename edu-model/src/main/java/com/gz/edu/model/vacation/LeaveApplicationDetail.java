package com.gz.edu.model.vacation;

import java.io.Serializable;
import java.util.Date;

public class LeaveApplicationDetail implements Serializable {
    private Integer id;

    private Integer vaId;

    private String descript;

    private Date uodateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVaId() {
        return vaId;
    }

    public void setVaId(Integer vaId) {
        this.vaId = vaId;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }

    public Date getUodateTime() {
        return uodateTime;
    }

    public void setUodateTime(Date uodateTime) {
        this.uodateTime = uodateTime;
    }
}