package com.gz.edu.controller.vacation;

import com.gz.edu.model.sys.JSONMessage;
import com.gz.edu.model.user.User;
import com.gz.edu.model.vacation.LeaveApplication;
import com.gz.edu.service.vacation.LeaveApplicationService;
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
 * 请假申请
 *
 * @author hzz
 */
@Controller
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    /**
     * 跳转请假申请列表页面
     *
     * @return
     */
    @RequestMapping("/leaveApplication")
    @RequiresPermissions(value = {"leaveApplication"}, logical = Logical.OR)
    public String leaveApplication(ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        map.put("user", user);
        List<LeaveApplication> list = leaveApplicationService.searchListByUserId(Integer.parseInt(user.getUserId()));
        map.put("lists", list);
        return "/edu/vacation/leave_application_list";
    }

    /**
     * 跳转请假申请页面
     *
     * @return
     */
    @RequestMapping("/toApplication")
    @RequiresPermissions(value = {"toApplication"}, logical = Logical.OR)
    public String toApplication(ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        map.put("user", user);
        return "/edu/vacation/leave_application_add";
    }

    /**
     * 保存请假申请
     */
    @RequestMapping("/saveApplication")
    @RequiresPermissions(value = {"saveApplication"}, logical = Logical.OR)
    public @ResponseBody
    JSONMessage saveApplication(LeaveApplication leaveApplication) {
        JSONMessage m = new JSONMessage();
        if (leaveApplication != null) {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            leaveApplication.setUserId(Integer.parseInt(user.getUserId()));
            leaveApplication.setOccupyDays(leaveApplication.getOccupyWorkDays());
            leaveApplication.setState(1);
            leaveApplicationService.saveLeaveApplication(leaveApplication, user);
            m.setFlag(JSONMessage.SUCCESS);
            m.setMsg("保存成功！");
            return m;
        }
        m.setFlag(JSONMessage.FAIL);
        m.setMsg("保存失败！");
        return m;
    }

}
