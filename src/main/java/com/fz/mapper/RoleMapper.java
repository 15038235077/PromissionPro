package com.fz.mapper;

import com.fz.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Role record);

    Role selectByPrimaryKey(Long rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * 保存角色权限对应关系
     * @param rid
     * @param pid
     */
    void insertRoleAndPermissionRel(@Param(("rid")) Long rid, @Param("pid") Long pid);

    /**
     * 删除角色与权限之间的关系
     * @param rid
     */
    void deletePermissionRel(Long rid);

    /**
     * 根据用户获取角色id
     * @param id
     * @return
     */
    List<Long> getRoleByEid(Long id);
}