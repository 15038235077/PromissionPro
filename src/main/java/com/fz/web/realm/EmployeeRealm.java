package com.fz.web.realm;

import com.fz.domain.Employee;
import com.fz.service.IEmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

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
     *  授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     *   认证
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
        if (employee == null){
            return null;
        }
        //开始认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(employee, employee.getPassword(), this.getName());
        return simpleAuthenticationInfo;
    }
}
