package com.fz.service;

import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
import com.fz.domain.Role;

/**
 * @ClassName IRoleService
 * @Description TODO
 * @Author fz
 * @Date 2019/3/23 13:47
 * @Version 1.0.0
 **/
public interface IRoleService {
     PageListRes getRoles(QueryVo vo);

     void saveRole(Role role);
}
