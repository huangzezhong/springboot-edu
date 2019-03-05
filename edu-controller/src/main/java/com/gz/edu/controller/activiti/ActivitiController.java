package com.gz.edu.controller.activiti;

import com.gz.edu.activiti.ActivitiUtils;
import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.sys.JSONMessage;
import com.gz.edu.model.user.User;
import com.gz.edu.service.activiti.ActivitiService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;


@Controller
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 任务回退
     *
     * @return
     */
    @RequestMapping("/rollBack")
    @ResponseBody
    public JSONMessage rollBack(@RequestParam(value = "userId", required = true) String userId) {
        JSONMessage m = new JSONMessage();
        try {
            List<Task> tasks = ActivitiUtils.getTasksByAssignee(userId);
            if (CollectionUtils.isNotEmpty(tasks)) {
                activitiService.rollBackTask(tasks.get(0).getId());
                m.setFlag(JSONMessage.SUCCESS);
                m.setMsg("驳回成功！");
            } else {
                m.setFlag(JSONMessage.FAIL);
                m.setMsg("驳回失败！");
            }
        } catch (ServiceException e) {
            m.setFlag(JSONMessage.FAIL);
            m.setMsg("驳回失败！");
        }
        return m;
    }


}
