package com.fz.web;

import com.fz.domain.AjaxRes;
import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
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

    @RequestMapping("/employee")
    public String employee(){
        return "employee";
    }

    /**
     * 查询员工
     */
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo queryVo){
        PageListRes pageListRes= employeeService.getEmployee(queryVo);
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
    /**
     * 更新员工表单
     */
    @RequestMapping("/updateEmployee")
    @ResponseBody
    public AjaxRes updateEmployee(Employee employee){
        System.out.println("come in");

        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateEmployee(employee);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    /**
     * 接受离职请求
     */
    @RequestMapping("/updateState")
    @ResponseBody
    public AjaxRes updateState(Long id){
        System.out.println(id);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateState(id);
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
