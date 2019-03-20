package com.fz.web;

import com.fz.domain.Department;
import com.fz.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName DepartmentController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/19 21:36
 * @Version 1.0.0
 **/
@Controller
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    //返回json格式
    @RequestMapping("/departList")
    @ResponseBody
    public List<Department> departList() {
        List<Department> departmentList = departmentService.getDepartmentList();
        return departmentList;
    }
}
