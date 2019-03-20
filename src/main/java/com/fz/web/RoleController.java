package com.fz.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 21:35
 * @Version 1.0.0
 **/
@Controller
public class RoleController {
    @RequestMapping("/role")
    public String role(){
        return "role";
    }
}
