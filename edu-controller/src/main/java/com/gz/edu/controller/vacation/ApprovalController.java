package com.gz.edu.controller.vacation;

import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.sys.JSONMessage;
import com.gz.edu.model.user.User;
import com.gz.edu.model.vacation.LeaveApplication;
import com.gz.edu.service.vacation.LeaveApplicationService;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 审批
 *
 * @author hzz
 */
@Controller
public class ApprovalController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    /**
     * 跳转到审批页面
     *
     * @return
     */
    @RequestMapping("/approval")
    public String toApproval(ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //查询代办事项
        List<LeaveApplication> list = leaveApplicationService.selectUnHandleApplication(user.getUserId());
        map.put("user", user);
        map.put("lists", list);
        return "/edu/vacation/approval_list";
    }

    /**
     * 审批
     */
    @RequestMapping("/saveApproval")
    @RequiresPermissions(value = {"saveApproval"}, logical = Logical.OR)
    public @ResponseBody
    JSONMessage saveApproval() {
        JSONMessage m = new JSONMessage();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            leaveApplicationService.saveApproval(user);
            m.setFlag(JSONMessage.SUCCESS);
            m.setMsg("审批成功！");
        } catch (ServiceException e) {
            m.setFlag(JSONMessage.FAIL);
            m.setMsg("审批失败！");
        }
        return m;
    }

    /**
     * 驳回审批
     */
    @RequestMapping("/revokeApproval")
    @RequiresPermissions(value = {"revokeApproval"}, logical = Logical.OR)
    public @ResponseBody
    JSONMessage revokeApproval(String staffId) {
        JSONMessage m = new JSONMessage();
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            leaveApplicationService.revokeApproval(user, staffId);
            m.setFlag(JSONMessage.SUCCESS);
            m.setMsg("驳回审批成功！");
        } catch (ServiceException e) {
            m.setFlag(JSONMessage.FAIL);
            m.setMsg("驳回审批失败！");
        }
        return m;
    }

}
