package com.fz.service;

import com.fz.domain.AjaxRes;
import com.fz.domain.Menu;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;

import java.util.List;

public interface IMenuService {
    PageListRes getMenuList(QueryVo vo);

    List<Menu> parentList();

    void saveMenu(Menu menu);

    AjaxRes updateMenu(Menu menu);

    AjaxRes deleteMenu(Long id);

    List<Menu> getTreeData();
}
