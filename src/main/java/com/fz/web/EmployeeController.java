package com.fz.web;

import com.fz.domain.AjaxRes;
import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName EmployeeController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 21:30
 * @Version 1.0.0
 **/
@Controller
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    private String username;
    private String pasword;
    @RequestMapping("/employee")
    public String employee(){
        return "employee";
    }

    /**
     * 查询员工
     */
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(){
        PageListRes pageListRes= employeeService.getEmployee();
        System.out.println("11111111111");
        return pageListRes;
    }
    /**
     * 接受员工添加表单
     */
    @RequestMapping("/saveEmployee")
    @ResponseBody
    public AjaxRes saveEmployee(Employee employee){
        System.out.println("2222222222222");
        System.out.println(employee.toString());
        System.out.println(employee.getUsername());
        System.out.println(employee.getPassword());
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employee.setStatus(true);
            employeeService.saveEmployee(employee);
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
