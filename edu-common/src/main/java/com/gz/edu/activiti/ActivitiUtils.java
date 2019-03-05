package com.gz.edu.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.List;

public class ActivitiUtils {

    /**
     * 当前用户-->当前用户正在执行的任务--->当前正在执行的任务的piid-->该任务所在的流程实例
     *
     * @param assignee
     * @return
     */
    public static List<ProcessInstance> getPIByUser(String assignee) {
        List<ProcessInstance> pis = new ArrayList<ProcessInstance>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        /**
         * 该用户正在执行的任务
         */
        List<Task> tasks = getTasksByAssignee(assignee);
        for (Task task : tasks) {
            String piid = task.getProcessInstanceId();
            ProcessInstance pi = processEngine.getRuntimeService()
                    .createProcessInstanceQuery()
                    .processInstanceId(piid)
                    .singleResult();
            pis.add(pi);
        }
        return pis;
    }

    /**
     * 根据指派人获取任务列表
     *
     * @param assignee 指派人
     * @return
     */
    public static List<Task> getTasksByAssignee(String assignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();
        return tasks;
    }

    /**
     * 根据流程实例id获取任务列表
     *
     * @param instanceId 流程实例ID
     * @return
     */
    public static List<Task> getTasksByInstanceId(String instanceId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .processDefinitionId(instanceId)
                .list();
        return tasks;
    }

}
