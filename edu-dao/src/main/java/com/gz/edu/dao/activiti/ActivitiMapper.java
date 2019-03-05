package com.gz.edu.dao.activiti;

import com.gz.edu.model.activiti.EduDeployment;
import com.gz.edu.model.activiti.RollBackTaskVarObj;
import org.springframework.stereotype.Repository;

/**
 * 自定义一些工作流程操作
 */
@Repository
public interface ActivitiMapper {

    void updateOldResporityByDeployInfo(EduDeployment eduDeployment);

    /**
     * 1、删除历史表ACT_HI_TASKINST  没完成的任务
     ***/
    public int deleteHisTask(RollBackTaskVarObj rollBackTaskVarObj);

    /**
     * 2、更新ACT_HI_TASKINST  已完成的任务完成时间为NULL
     ***/
    public int updateJumpTask(RollBackTaskVarObj rollBackTaskVarObj);

    /**
     * 3、获取linkId
     ***/
    public String getLinkId(String taskId);

    /**
     * 4、更新成NULL 解除任务关联
     ***/
    public int unlockTask(String id);

    /**
     * 5、更新成taskId 解除任务关联
     ***/
    public int updateRunTask(RollBackTaskVarObj rollBackTaskVarObj);

    /**
     * 6、更新成taskId 解除任务关联
     ***/
    public int updateLinkTask(RollBackTaskVarObj rollBackTaskVarObj);

    /**
     * 7、更新成taskId 解除任务关联
     ***/
    public int updateExecActId(RollBackTaskVarObj rollBackTaskVarObj);

}
