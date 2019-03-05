package com.gz.edu.controller.user;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("/userList")
    @RequiresPermissions(value = {"userList"}, logical = Logical.OR)
    public String userManage() {
        return "edu/user/user_list";
    }

}
