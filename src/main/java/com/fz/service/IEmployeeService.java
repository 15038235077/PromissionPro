package com.fz.service;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;

public interface IEmployeeService {
    /**
     * 查询员工
     */
    PageListRes getEmployee();

    /**
     * 保存员工信息
     */
    void saveEmployee(Employee employee);
}
