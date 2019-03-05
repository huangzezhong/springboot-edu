package com.gz.edu.model.activiti;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RollBackTaskVarObj implements Serializable {

    private String id;
    private String taskId;
    private String jumpTaskId;
    private String execId;

    public RollBackTaskVarObj() {

    }

    public RollBackTaskVarObj(String taskId, String jumpTaskId, String execId) {
        this.taskId = taskId;
        this.jumpTaskId = jumpTaskId;
        this.execId = execId;
    }

}
