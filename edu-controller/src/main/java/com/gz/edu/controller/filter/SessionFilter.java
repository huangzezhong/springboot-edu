/*
package com.gz.edu.controller.filter;

import com.google.common.collect.Maps;
import com.gz.edu.model.user.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@WebFilter(filterName = "sessionFilter",urlPatterns = {"/*"})
public class SessionFilter implements Filter {

    private static Map<String,String> noFilterUrl = Maps.newHashMap();

    static{
        noFilterUrl.put("toLogin","toLogin");
        noFilterUrl.put("login","login");
        noFilterUrl.put("*.js","*.js");
        noFilterUrl.put("*.css","*.css");
        noFilterUrl.put("*.png","*.png");
        noFilterUrl.put("*.jpg","*.jpg");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uri = request.getRequestURI().replace("/","");
        if (this.isNoFilterUrl(uri)) {
            filterChain.doFilter(request,response);
            return;
        }
        //校验用户是否登录
        HttpSession session = request.getSession();
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        User user = (User)session.getAttribute("USER_SESSION");
        if (null == user) {
            String repURL = path + "/loginOut?url=" + URLEncoder.encode(basePath, "UTF-8");
            response.sendRedirect(repURL);
            return;
        } else {
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }

    private static boolean isNoFilterUrl(String uri){
        if (noFilterUrl.get(uri) != null) {
            return true;
        }
        for (Map.Entry<String,String> url:noFilterUrl.entrySet()) {
            String key = url.getKey();
            if (key.startsWith("*.")) {
                int length = key.length();
                String endStr = key.substring(1,length);
                if (uri.endsWith(endStr)) {
                    return true;
                }
            }
        }
        return false;
    }

}
*/
