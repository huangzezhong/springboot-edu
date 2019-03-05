package com.gz.edu.service.vacation.impl;

import com.gz.edu.activiti.ActivitiUtils;
import com.gz.edu.dao.user.UserMapper;
import com.gz.edu.dao.vacation.LeaveApplicationDetailMapper;
import com.gz.edu.dao.vacation.LeaveApplicationMapper;
import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.user.User;
import com.gz.edu.model.vacation.LeaveApplication;
import com.gz.edu.model.vacation.LeaveApplicationDetail;
import com.gz.edu.service.vacation.LeaveApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LeaveApplicationServiceImpl implements LeaveApplicationService, JavaDelegate {

    //请假流程key
    private static final String PROCESS_INSTANCE_KEY = "vacation_01";

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;

    @Autowired
    private LeaveApplicationDetailMapper leaveApplicationDetailMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    //@Transactional
    public void saveLeaveApplication(LeaveApplication leaveApplication, User user) throws ServiceException {
        //保存到请假申请表
        leaveApplicationMapper.insert(leaveApplication);
        //通过businessId将流程与业务关联，对应act_ru_exectution中的business_key
        String businessId = leaveApplication.getId() + "";
        //部署流程在juint test中启动（待开发前台部署流程页面）

        //设置流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("staffId", user.getUserId());
        //查找上一级领导
        User u = new User();
        /*if (leaveApplication.getOccupyDays() < 3) {
            u.setUsername("部门领导");
        } else {
            u.setUsername("老板");
        }*/
        u.setUsername("部门领导");
        User u1 = userMapper.selectUserByName(u);
        variables.put("leaderId", u1.getUserId());

        /*u.setUsername("老板");
        User u2 = userMapper.selectUserByName(u);
        variables.put("bossId", u2.getUserId());*/
        variables.put("vaDate", leaveApplication.getOccupyDays());
        //启动请假流程实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_INSTANCE_KEY, businessId, variables);  //这个是查看数据库中act_re_procdef表
        //查询任务ID
        List<Task> taskList = this.queryCurretUserTaskByAssignerr(user.getUserId());
        Task task = taskList.get(0);
        //提交请假申请-查看act_ru_task表
        processEngine.getTaskService().complete(task.getId());
        //增加请假详情记录
        LeaveApplicationDetail detail = new LeaveApplicationDetail();
        DateTime dateTime = new DateTime();
        String now = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        detail.setDescript(now + " " + user.getUsername() + "发起了请假流程!");
        detail.setVaId(Integer.parseInt(businessId));
        leaveApplicationDetailMapper.insert(detail);
    }

    public static void main(String args[]) {
        //启动请假流程实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务ID
        List<Task> taskList = new LeaveApplicationServiceImpl()
                .queryCurretUserTaskByAssignerr("4");
        Task task = taskList.get(0);
        //提交请假申请-查看act_ru_task表
        processEngine.getTaskService().complete(task.getId());
    }

    @Override
    public List<Task> queryCurretUserTaskByAssignerr(String userId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        return processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(userId)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    @Override
    public List<LeaveApplication> selectUnHandleApplication(String userId) throws ServiceException {
        List<ProcessInstance> instances = ActivitiUtils.getPIByUser(userId);
        if (CollectionUtils.isNotEmpty(instances)) {
            List<String> businessList = new ArrayList<String>();
            for (ProcessInstance instance : instances) {
                //获取与业务相关的key
                String businessKey = instance.getBusinessKey();
                businessList.add(businessKey);
            }
            //根据业务id查询请假申请列表
            Map map = new HashMap();
            map.put("idList", businessList);
            return leaveApplicationMapper.selectListByIds(map);
        }
        return null;
    }

    @Override
    public List<LeaveApplication> searchListByUserId(Integer userId) throws ServiceException {
        return leaveApplicationMapper.selectListByUserId(userId);
    }

    @Override
    @Transactional
    public void saveApproval(User user) throws ServiceException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务ID
        List<Task> taskList = this.queryCurretUserTaskByAssignerr(user.getUserId());
        List<ProcessInstance> instances = ActivitiUtils.getPIByUser(user.getUserId());
        for (ProcessInstance instance : instances) {
            //增加请假详情记录
            LeaveApplicationDetail detail = new LeaveApplicationDetail();
            DateTime dateTime = new DateTime();
            String now = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            detail.setDescript(now + " " + user.getUsername() + "处理了流程, 结论: 同意. 到达\"审批通过\", 流程结束!");
            detail.setVaId(Integer.parseInt(instance.getBusinessKey()));
            leaveApplicationDetailMapper.insert(detail);
        }
        for (Task task : taskList) {
            //设置流程变量
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("leaderId", 5);
            variables.put("isRevoke", false);
            //完成审批
            processEngine.getTaskService().complete(task.getId(), variables); //查看act_ru_task表
        }

    }

    @Override
    public void revokeApproval(User user, String staffId) throws ServiceException {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务ID
        List<Task> taskList = this.queryCurretUserTaskByAssignerr(user.getUserId());
        List<ProcessInstance> instances = ActivitiUtils.getPIByUser(user.getUserId());
        for (ProcessInstance instance : instances) {
            //增加请假详情记录
            LeaveApplicationDetail detail = new LeaveApplicationDetail();
            DateTime dateTime = new DateTime();
            String now = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            detail.setDescript(now + " " + user.getUsername() + "处理了流程, 请假天数太多, 请重新提交!");
            detail.setVaId(Integer.parseInt(instance.getBusinessKey()));
            leaveApplicationDetailMapper.insert(detail);
        }
        for (Task task : taskList) {
            //设置流程变量
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("leaderId", staffId);
            variables.put("isRevoke", true);
            //完成审批
            processEngine.getTaskService().complete(task.getId(), variables); //查看act_ru_task表
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("--------serviceTask开始执行！-------");
    }
}
