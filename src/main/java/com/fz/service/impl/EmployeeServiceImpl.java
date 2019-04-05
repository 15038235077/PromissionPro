package com.fz.service.impl;

import com.fz.domain.Employee;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
import com.fz.domain.Role;
import com.fz.mapper.EmployeeMapper;
import com.fz.service.IEmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Md5Hash;
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
        //加密密码   加密数据   加密参考物  散列次数
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
        employee.setPassword(md5Hash.toString());

        //添加员工
        employeeMapper.insert(employee);
        //保存角色关系表
        for (Role role: employee.getRoles()){
            employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        //打破与角色之间的关系
        employeeMapper.deleteRoleRel(employee.getId());
        //更新员工
        employeeMapper.updateByPrimaryKey(employee);
        //重新建立角色关系
        for (Role role: employee.getRoles()){
            employeeMapper.insertEmployeeRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateState(id);
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public Employee getEmployeeWithUserName(String username) {
        return employeeMapper.getEmployeeWithUserName(username);
    }

    /**
     * 根据用户id查询角色编号名称
     * @param id
     * @return
     */
    @Override
    public List<String> getRolesById(Long id) {
        return employeeMapper.getRolesById(id);
    }

    /**
     * 根据用户id查询权限编号名称
     * @param id
     * @return
     */
    @Override
    public List<String> getPermissionById(Long id) {
        return employeeMapper.getPermissionById(id);
    }
}
