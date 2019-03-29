package com.fz.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fz.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @ClassName MyFormFilter
 * @Description TODO
 * @Author fz
 * @Date 2019/3/29 23:58
 * @Version 1.0.0
 **/
public class MyFormFilter extends FormAuthenticationFilter {

    /**
     * 成功调用
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("认证成功");

        response.setCharacterEncoding("utf-8");

        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMsg("登录成功");
        //把对象转成json格式字符串
        String s = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(s);
        return false;
    }

    /**
     * 失败调用
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("认证失败");
        response.setCharacterEncoding("utf-8");

        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        if (e != null) {
            String name = e.getClass().getName();
            if (name.equals(UnknownAccountException.class.getName())) {
                ajaxRes.setMsg("账号不正确");
            } else if (name.equals(IncorrectCredentialsException.class.getName())){
                ajaxRes.setMsg("密码错误");
            }else {
                ajaxRes.setMsg("未知错误");
            }
        }
        try {
            //把对象转成json格式字符串
            String s = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(s);
        } catch (Exception e1){
            e1.printStackTrace();
        }
        return false;
    }
}
