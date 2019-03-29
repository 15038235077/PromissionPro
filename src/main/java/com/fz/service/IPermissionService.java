package com.fz.service;

import com.fz.domain.Permission;

import java.util.List;

public interface IPermissionService {
     List<Permission> getpermissions();

    List<Permission> getPermissionByRid(Long rid);
}
