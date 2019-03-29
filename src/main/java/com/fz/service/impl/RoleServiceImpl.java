package com.fz.service.impl;

import com.fz.domain.PageListRes;
import com.fz.domain.Permission;
import com.fz.domain.QueryVo;
import com.fz.domain.Role;
import com.fz.mapper.RoleMapper;
import com.fz.service.IRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author fz
 * @Date 2019/3/23 13:48
 * @Version 1.0.0
 **/
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageListRes getRoles(QueryVo vo) {
        //开始调用mapper
        Page<Object> objects = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Role> roles = roleMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(objects.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public void saveRole(Role role) {
        //先保存角色
        roleMapper.insert(role);
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        //再保存角色与权限之间的关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRid(), permission.getPid());
        }
    }

    /**
     * 更新角色
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        //先打破角色与权限之间的关系
        roleMapper.deletePermissionRel(role.getRid());
        //更新角色
        roleMapper.updateByPrimaryKey(role);
        //重新创建关系
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermissionRel(role.getRid(), permission.getPid());
        }
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    public void deleteRole(Long id) {
        //删除所有关联权限关系
        roleMapper.deletePermissionRel(id);
        System.err.println(id);
        //删除对应角色
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<Role> roleList() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Long> getRoleByEid(Long id) {
        return roleMapper.getRoleByEid(id);
    }
}

