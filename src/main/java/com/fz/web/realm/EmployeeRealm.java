package com.fz.web.realm;

import com.fz.domain.Employee;
import com.fz.service.IEmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EmployeeRealm
 * @Description TODO
 * @Author fz
 * @Date 2019/3/29 23:23
 * @Version 1.0.0
 **/
public class EmployeeRealm extends AuthorizingRealm {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 授权
     * web  doGetAuthorizationInfo 什么时候调用
     * 发现路径对应的方法上边 有相应的注解 就会调用doGetAuthorizationInfo
     * 页面当中有授权标签，也会调用doGetAuthorizationInfo方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权调用");
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        //根据当前用户，进行查询角色和权限


        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        roles = employeeService.getRolesById(employee.getId());
        System.out.println("roles   " + roles);
        permissions = employeeService.getPermissionById(employee.getId());
        System.out.println("permissions   " + permissions);
        //给授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("来到了认证-----------");
        /* 获取身份信息*/
        String username = (String) token.getPrincipal();
        System.out.println(username);
        //到数据库查询有没有相应用户
        Employee employee = employeeService.getEmployeeWithUserName(username);
        System.out.println(employee);
        if (employee == null) {
            return null;
        }
        //开始认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                employee, employee.getPassword(),
                ByteSource.Util.bytes(employee.getUsername()),
                this.getName());
        return simpleAuthenticationInfo;
    }
}
