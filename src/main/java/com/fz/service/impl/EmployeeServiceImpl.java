package com.fz.service.impl;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.mapper.EmployeeMapper;
import com.fz.service.IEmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName EmployeeServiceImpl
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 22:33
 * @Version 1.0.0
 **/
@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageListRes getEmployee() {

        Page<Object> page = PageHelper.startPage(1, 5);
        List<Employee> employees = employeeMapper.selectAll();

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);
        return pageListRes;

    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }
}
