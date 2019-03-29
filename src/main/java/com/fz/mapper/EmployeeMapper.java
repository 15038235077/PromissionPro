package com.fz.mapper;

import com.fz.domain.Employee;
import com.fz.domain.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Long id);

    void deleteRoleRel(Long id);

    /**
     * 保存员工和角色关系
     * @param id
     * @param rid
     */
    void insertEmployeeRoleRel(@Param("id") Long id, @Param("rid") Long rid);
}