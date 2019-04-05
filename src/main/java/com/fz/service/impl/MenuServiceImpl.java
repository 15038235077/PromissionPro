package com.fz.service.impl;

import com.fz.domain.AjaxRes;
import com.fz.domain.Employee;
import com.fz.domain.Menu;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
import com.fz.mapper.MenuMapper;
import com.fz.service.IMenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuServiceImpl
 * @Description TODO
 * @Author fz
 * @Date 2019/4/5 17:49
 * @Version 1.0.0
 **/
@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public PageListRes getMenuList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Menu> menus = menuMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(menus);

        return pageListRes;
    }

    @Override
    public List<Menu> parentList() {
        return menuMapper.selectAll();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.saveMenu(menu);
    }

    @Override
    public AjaxRes updateMenu(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        /*判断 选择的父菜单 是不是自己的子菜单*/
        /*先取出当前选择父菜单id*/
        Long id = menu.getParent().getId();
        /*查询该id对应的menu*/
        Long parent_id = menuMapper.selectParentId(id);
        if (menu.getId().equals(parent_id)) {
            ajaxRes.setMsg("不能设置自己的子菜单为父菜单");
            ajaxRes.setSuccess(false);
            return ajaxRes;
        }
        /*更新*/
        try {
            menuMapper.updateByPrimaryKey(menu);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("保存失败");
            System.out.println("======================================");
            System.out.println(e);
        }
        return ajaxRes;
    }

    @Override
    public AjaxRes deleteMenu(Long id) {

        AjaxRes ajaxRes = new AjaxRes();
        try {
            /*1.打破菜单关系*/
            menuMapper.updateMenuRel(id);
            /*2.删除记录*/
            menuMapper.deleteByPrimaryKey(id);
            ajaxRes.setMsg("删除成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("删除失败");
            System.out.println(e);
        }
        return ajaxRes;
    }

    @Override
    public List<Menu> getTreeData() {
        List<Menu> treeData = menuMapper.getTreeData();
        /*
        判断当前用户有没有对应的权限
        如果没有就从集合当中移除
        */
        /*获取用户 判断用户是否是管理员 是管理就不需要做判断*/
//        Subject subject = SecurityUtils.getSubject();
//        /*当前的用户*/
//        Employee employee = (Employee)subject.getPrincipal();
//        if (!employee.getAdmin()){
//            /*做检验权限*/
//            checkPermission(treeData);
//        }
        return treeData;
    }
}
