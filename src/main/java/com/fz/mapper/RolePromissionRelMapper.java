package com.fz.mapper;

import com.fz.domain.RolePromissionRel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePromissionRelMapper {
    int deleteByPrimaryKey(@Param("rid") Long rid, @Param("pid") Long pid);

    int insert(RolePromissionRel record);

    List<RolePromissionRel> selectAll();
}