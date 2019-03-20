package com.fz.service.impl;

import com.fz.domain.Department;
import com.fz.mapper.DepartmentMapper;
import com.fz.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DepartmentServiceImpl
 * @Description TODO
 * @Author fz
 * @Date 2019/3/19 21:39
 * @Version 1.0.0
 **/
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartmentList() {
        List<Department> departmentList = departmentMapper.selectAll();
        return departmentList;
    }
}
