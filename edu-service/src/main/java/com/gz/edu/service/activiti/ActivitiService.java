package com.gz.edu.service.activiti;

import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.activiti.EduDeployment;

public interface ActivitiService {

    /**
     * 根据部署信息更新老版本的资源文件（包括png和xml）
     *
     * @param eduDeployment
     */
    public void updateOldResporityByDeployInfo(EduDeployment eduDeployment) throws ServiceException;

    /**
     * 回退任务
     *
     * @param taskId
     * @throws ServiceException
     */
    public void rollBackTask(String taskId) throws ServiceException;

}
