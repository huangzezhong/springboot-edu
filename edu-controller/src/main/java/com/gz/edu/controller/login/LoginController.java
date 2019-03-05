package com.gz.edu.controller.login;


import com.google.common.hash.Hashing;
import com.gz.edu.model.sys.JSONMessage;
import com.gz.edu.model.user.User;
import com.gz.edu.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("USER_SESSION") != null) {
            session.setAttribute("USER_SESSION", null);
        }
        return "/login";
    }

    /**
     * 传统方式登录
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/toIndex")
    public String login(HttpServletRequest request, User user) {
        if (user != null) {
            User u = userService.qryUserByUserName(user);
            String pwd = user.getPassword();
            //登录成功
            if (u != null
                    && u.getPassword().equals(
                    Hashing.md5().hashBytes(pwd.getBytes()).toString())) {
                request.getSession().setAttribute("USER_SESSION", u);
            }
        }
        return "/index";
    }

    /**
     * shiro方式登录(后端处理方式)
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/toIndexByShiro")
    public String shiroLogin(HttpServletRequest request, User user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.info("用户验证不通过:", e.getMessage());
            //跳转至自定义异常处理
            throw new AccountException(e);
        }
        return "/index";
    }

    /**
     * shiro方式登录(前后端分离方式)
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/toIndexByShiroAjax")
    public @ResponseBody
    JSONMessage toIndexByShiroAjax(HttpServletRequest request, User user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        JSONMessage m = new JSONMessage();
        m.setFlag(JSONMessage.SUCCESS);
        m.setMsg("登录成功！");
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.info("用户验证不通过:", e.getMessage());
            m.setFlag(JSONMessage.FAIL);
            m.setMsg("用户名或密码不正确！");
        }
        return m;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "md5";
        String credentials = "123";
        User user = new User("老板", credentials);
        Object salt = ByteSource.Util.bytes(user.getCredentialsSalt());
        int hashIterations = 2;
        Object result = new SimpleHash(hashAlgorithmName, credentials.getBytes(), salt, hashIterations);
        System.out.println(result);
    }

}
