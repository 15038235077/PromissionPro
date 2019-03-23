package com.fz.service.impl;

import com.fz.domain.Permission;
import com.fz.mapper.PermissionMapper;
import com.fz.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @Author fz
 * @Date 2019/3/23 14:14
 * @Version 1.0.0
 **/
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getpermissions() {
        List<Permission> permissions = permissionMapper.selectAll();
        return permissions;
    }
}
