package com.fz.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fz.domain.AjaxRes;
import com.fz.domain.Department;
import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
import com.fz.service.IEmployeeService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @RequiresPermissions("employee:index")
    public String employee() {
        return "employee";
    }

    /**
     * 查询员工
     */
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo queryVo) {
        PageListRes pageListRes = employeeService.getEmployee(queryVo);
        System.out.println("11111111111");
        return pageListRes;
    }

    /**
     * 接受员工添加表单
     */
    @RequestMapping("/saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public AjaxRes saveEmployee(Employee employee) {
        System.out.println("2222222222222");
        System.out.println(employee.toString());
        Employee employeeWithUserName = employeeService.getEmployeeWithUserName(employee.getUsername());
        AjaxRes ajaxRes = new AjaxRes();
        if (employeeWithUserName != null) {
            ajaxRes.setMsg("该用户已经存在");
            ajaxRes.setSuccess(false);
            return ajaxRes;
        }
        System.out.println("该用户不存在");
        System.out.println(employee.getUsername());
        System.out.println(employee.getPassword());

        try {
            employee.setStatus(true);
            employeeService.saveEmployee(employee);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        } catch (Exception e) {
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
    @RequiresPermissions("employee:edit")
    public AjaxRes updateEmployee(Employee employee) {
        System.out.println("come in");

        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateEmployee(employee);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        } catch (Exception e) {
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
    @RequiresPermissions("employee:delete")
    public AjaxRes updateState(Long id) {
        System.out.println(id);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateState(id);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        return ajaxRes;
    }

    @ExceptionHandler(AuthorizationException.class)
    public void handleShiroException(HandlerMethod method, HttpServletResponse response) throws IOException {
        //跳转到一个界面  界面提示没有权限

        //判断是不是json请求 如果是将 返回Json给浏览器 让他自己来做跳转

        //获取方法上的注解
        ResponseBody methodAnnotation = method.getMethodAnnotation(ResponseBody.class);
        if (methodAnnotation != null) {
            //没有权限操作
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setMsg("当前没有权限操作");
            ajaxRes.setSuccess(false);
            String s = new ObjectMapper().writeValueAsString(ajaxRes);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(s);
        } else {
            response.sendRedirect("nopermission.jsp");
        }
    }

    @RequestMapping("/download")
    @ResponseBody
    public void download(HttpServletResponse response) {
        try {
            System.out.println("-------------download----------------");
            //从数据库拉取数据
            QueryVo queryVo = new QueryVo();
            queryVo.setPage(1);
            queryVo.setRows(100);
            PageListRes res = employeeService.getEmployee(queryVo);
            List<Employee> employees = (List<Employee>) res.getRows();
            //写入excel
            HSSFWorkbook sheets = new HSSFWorkbook();
            HSSFSheet sheet = sheets.createSheet("员工数据");
            //创建一行 表头
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("用户名");
            row.createCell(2).setCellValue("入职日期");
            row.createCell(3).setCellValue("电话");
            row.createCell(4).setCellValue("邮件");
            row.createCell(5).setCellValue("是否管理员");
            row.createCell(6).setCellValue("是否在职");
            //取出每个员工
            for (int i = 0; i < employees.size(); i++) {
                Employee employee = employees.get(i);
                HSSFRow rowData = sheet.createRow(i + 1);
                rowData.createCell(0).setCellValue(employee.getId());
                rowData.createCell(1).setCellValue(employee.getUsername());
                if (employee.getInputtime() != null) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String format = simpleDateFormat.format(employee.getInputtime());
                    rowData.createCell(2).setCellValue(format);
                } else {
                    rowData.createCell(2).setCellValue("");
                }

                rowData.createCell(3).setCellValue(employee.getTel());
                rowData.createCell(4).setCellValue(employee.getEmail());
                if (employee.getAdmin()) {
                    rowData.createCell(5).setCellValue("是");
                } else {
                    rowData.createCell(5).setCellValue("否");
                }
                if (employee.getStatus()) {
                    rowData.createCell(6).setCellValue("在职");
                } else {
                    rowData.createCell(6).setCellValue("离职");
                }
            }

            //响应给浏览器
            String fileName = new String("员工数据.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("content-Disposition", "attachment;filename=" + fileName);
            sheets.write(response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板
     */
    @RequestMapping("downloadTml")
    @ResponseBody
    public void downloadTml(HttpServletRequest request, HttpServletResponse response) {
        FileInputStream fileInputStream = null;
        try {
            String fileName = new String("EmployeeTml.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("content-Disposition", "attachment;filename=" + fileName);
            /* 获取文件路径*/
            String realPath = request.getSession().getServletContext().getRealPath("static/ExcelTml.xls");
            fileInputStream = new FileInputStream(realPath);
            //读入文件写到输出流中
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*配置文件解析器 */
    @RequestMapping("/uploadExcelFile")
    @ResponseBody
    public AjaxRes uploadExcelFile(MultipartFile excel) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setMsg("导入成功");
            ajaxRes.setSuccess(true);
            HSSFWorkbook sheets = new HSSFWorkbook(excel.getInputStream());
            HSSFSheet sheet = sheets.getSheetAt(0);
            //获取最大行号
            int lastRowNum = sheet.getLastRowNum();
     //       System.err.println(lastRowNum + "=-----------------------------");
            Row employeeRow = null;
            for (int i = 1; i <= lastRowNum; i++) {
                employeeRow = sheet.getRow(i);
                Employee employee = new Employee();
                employee.setUsername(String.valueOf(getCellValue(employeeRow.getCell(0))));
                employee.setPassword(String.valueOf(getCellValue(employeeRow.getCell(1))));
                employee.setInputtime((Date) getCellValue(employeeRow.getCell(2)));
                employee.setTel(String.valueOf(getCellValue(employeeRow.getCell(3))));
                Department department = new Department();
                department.setId((long) 1);
                department.setName("技术部");
                employee.setStatus(true);
                employee.setAdmin(false);
                employeeService.saveEmployee(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxRes.setMsg("导入失败");
            ajaxRes.setSuccess(false);
        }

        return ajaxRes;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
        }
        return cell;
    }
}
