package com.fz.web;

import com.fz.domain.Permission;
import com.fz.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author fz
 * @Date 2019/3/23 14:12
 * @Version 1.0.0
 **/
@Controller
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 查询所有权限
     */
    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        System.out.println("come in");
        List<Permission> getpermissions = permissionService.getpermissions();
        for (Permission permission : getpermissions){
            System.out.println(permission.getPname());
            System.out.println(permission.getPresource());
        }
        return permissionService.getpermissions();
    }
}
