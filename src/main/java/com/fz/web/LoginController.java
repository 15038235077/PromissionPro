package com.fz.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/29 23:17
 * @Version 1.0.0
 **/
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "redirect:login.jsp";
    }
}
