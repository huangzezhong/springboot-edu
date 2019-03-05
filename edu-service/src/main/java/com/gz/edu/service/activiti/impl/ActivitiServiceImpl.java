package com.gz.edu.service.activiti.impl;

import com.gz.edu.dao.activiti.ActivitiMapper;
import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.activiti.EduDeployment;
import com.gz.edu.model.activiti.RollBackTaskVarObj;
import com.gz.edu.service.activiti.ActivitiService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private ActivitiMapper activitiMapper;

    @Override
    public void updateOldResporityByDeployInfo(EduDeployment eduDeployment) throws ServiceException {
        activitiMapper.updateOldResporityByDeployInfo(eduDeployment);
    }

    @Override
    @Transactional
    public void rollBackTask(String taskId) throws ServiceException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task == null) {
            throw new ServiceException("回退失败");
        }
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        //4.使用流程实例对象获取BusinessKey
        String business_key = pi.getBusinessKey();
        //查询历史任务
        String processInstanceId = task.getProcessInstanceId();
        List<HistoricTaskInstance> hiList = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .orderByTaskCreateTime()
                .desc()
                .list();
        if (CollectionUtils.isEmpty(hiList)) {
            throw new ServiceException("回退失败");
        }
        String jumpTaskId = hiList.get(0).getId();
        String executionId = hiList.get(0).getExecutionId();
        if (StringUtils.isNotBlank(jumpTaskId)) {
            //插入审批记录
            //退回任务
            this.rollBackTaskDbOper(taskId, jumpTaskId, executionId);
        }
    }

    /**
     * 自定义操作流程表
     *
     * @param taskId
     * @param jumpTaskId
     * @param execId
     * @throws ServiceException
     */
    private void rollBackTaskDbOper(String taskId, String jumpTaskId, String execId) throws ServiceException {
        RollBackTaskVarObj rollBackTaskVarObj = new RollBackTaskVarObj(taskId, jumpTaskId, execId);
        activitiMapper.deleteHisTask(rollBackTaskVarObj);
        //更新跳转任务的完成时间 和状态
        activitiMapper.updateJumpTask(rollBackTaskVarObj);
        String linkId = activitiMapper.getLinkId(taskId);
        //更新taskId 解除关联 ACT_RU_IDENTITYLINK
        activitiMapper.unlockTask(linkId);
        //更新当前任务 名称 fromKey id
        activitiMapper.updateRunTask(rollBackTaskVarObj);
        //更新ACT_RU_IDENTITYLINK 的taskid
        activitiMapper.updateLinkTask(rollBackTaskVarObj);
        //更新execu的 act_id_
        activitiMapper.updateExecActId(rollBackTaskVarObj);
    }

}
