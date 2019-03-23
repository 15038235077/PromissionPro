package com.fz.service;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;

public interface IEmployeeService {
    /**
     * 查询员工
     */
    PageListRes getEmployee(QueryVo vo);

    /**
     * 保存员工信息
     */
    void saveEmployee(Employee employee);
    /**
     * 更新员工
     */
    void updateEmployee(Employee employee);
    /**
     * 更新员工状态
     */
    void updateState(Long id);
}
