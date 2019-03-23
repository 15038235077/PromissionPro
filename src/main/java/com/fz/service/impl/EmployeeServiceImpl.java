package com.fz.service.impl;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
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
    public PageListRes getEmployee(QueryVo vo) {

        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Employee> employees = employeeMapper.selectAll(vo);

        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);
        return pageListRes;

    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKey(employee);
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }
}
