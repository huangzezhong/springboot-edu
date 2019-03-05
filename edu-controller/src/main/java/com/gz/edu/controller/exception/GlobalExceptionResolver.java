package com.gz.edu.controller.exception;

import com.alibaba.fastjson.JSONObject;
import com.gz.edu.exception.ServiceException;
import com.gz.edu.model.sys.JSONMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 *
 * @author LinkinStar
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ServiceException.class)
    public @ResponseBody
    String serviceCommonExceptionHandler(ServiceException e, Model model) {
        //对捕获的异常进行处理并打印日志等，之后返回json数据，方式与Controller相同
        return "{'code':-1}";
    }

    /**
     * shiro权限异常
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ExceptionHandler(value = {
            AccountException.class,
            AuthorizationException.class,
            UnauthorizedException.class,
            UnauthenticatedException.class})
    public void exceptionHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (this.isAjaxRequest(request)) {
            JSONMessage m = new JSONMessage();
            m.setFlag(JSONMessage.FAIL);
            m.setMsg("您好，你没有该项操作的权限!\n如有疑问请联系管理员!");
            out.print(JSONObject.toJSONString(m));
        } else {
            Object contextPath = request.getContextPath();
            out.println("<html>");
            out.println("<script>");
            out.println("alert('您好，你没有该项操作的权限!\\n如有疑问请联系管理员!');");
            out.println("function exit(){window.location.href = '" + contextPath + "/login;}");
            out.println("</script>");
            out.println("<body>");
            out.println("您好，你没有该项操作的权限!如有疑问请联系管理员!<br><a href='javascript:exit();'>注销用户</a>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

}
