package com.fz.service;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;

import java.util.List;

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

    Employee getEmployeeWithUserName(String username);
    /**
     * 根据用户查询角色
     */
    List<String> getRolesById(Long id);
    /**
     * 根据用户查询权限
     */
    List<String> getPermissionById(Long id);
}
