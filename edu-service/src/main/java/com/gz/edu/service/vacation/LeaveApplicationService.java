package com.gz.edu.service.vacation;

import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.user.User;
import com.gz.edu.model.vacation.LeaveApplication;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

import java.util.List;

public interface LeaveApplicationService {

    /**
     * 保存请假申请
     *
     * @param leaveApplication
     * @throws ServiceException
     */
    public void saveLeaveApplication(LeaveApplication leaveApplication, User user) throws ServiceException;


    /**
     * 根据用户ID查询请假申请列表
     *
     * @param userId
     * @throws ServiceException
     */
    public List<LeaveApplication> searchListByUserId(Integer userId) throws ServiceException;

    /**
     * 审批
     *
     * @param user
     * @throws ServiceException
     */
    public void saveApproval(User user) throws ServiceException;

    /**
     * 驳回审批
     *
     * @param user
     * @throws ServiceException
     */
    public void revokeApproval(User user, String staffId) throws ServiceException;

    /**
     * 查询当前登陆人的所有任务
     *
     * @param userId
     * @return
     */
    public List<Task> queryCurretUserTaskByAssignerr(String userId);


    /**
     * 查询代办事项
     *
     * @param userId
     * @return
     * @throws ServiceException
     */
    public List<LeaveApplication> selectUnHandleApplication(String userId) throws ServiceException;

    /**
     * 保存请假处理详情-流程service task调用
     * @param execution
     * @throws ServiceException
     */
    //public void recordLeaveApplicationDetail(Execution execution) throws ServiceException;
}
