package com.gz.edu.controller.login;

import com.gz.edu.model.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        map.put("user", user);
        return "/edu/index";
    }

    @RequestMapping("/welcome_pass")
    public String welcome_pass(ModelMap map, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        map.put("user", user);
        map.put("sessionId", request.getSession().getId());
        return "/edu/welcome";
    }

}
