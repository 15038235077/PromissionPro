package com.fz.mapper;

import com.fz.domain.Employee;
import com.fz.domain.QueryVo;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);
}