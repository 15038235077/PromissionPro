package com.fz.web;

import com.fz.domain.AjaxRes;
import com.fz.domain.PageListRes;
import com.fz.domain.Permission;
import com.fz.domain.QueryVo;
import com.fz.domain.Role;
import com.fz.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 21:35
 * @Version 1.0.0
 **/
@Controller
public class RoleController {

    @Autowired
    IRoleService roleService;

    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    /**
     * 获取所有角色
     * @param vo
     * @return
     */
    @RequestMapping("/getRoles")
    @ResponseBody
    public PageListRes getRoles(QueryVo vo){
        return roleService.getRoles(vo);
    }

    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRoles(Role role){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            roleService.saveRole(role);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }
}
