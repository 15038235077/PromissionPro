package com.fz.web;

import com.fz.domain.AjaxRes;
import com.fz.domain.Menu;
import com.fz.domain.PageListRes;
import com.fz.domain.QueryVo;
import com.fz.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName MenuController
 * @Description TODO
 * @Author fz
 * @Date 2019/3/18 21:35
 * @Version 1.0.0
 **/
@Controller
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public PageListRes menuList(QueryVo vo) {
        //查询菜单
        PageListRes pageListRes = menuService.getMenuList(vo);
        System.out.println("=============================================================================================");
        System.out.println(pageListRes.getRows().toString());
        return pageListRes;
    }

    /**
     * 加载父菜单
     * @param
     * @return
     */
    @RequestMapping("/parentList")
    @ResponseBody
    public List<Menu> parentList() {
        //查询菜单
       return menuService.parentList();
    }

    @RequestMapping("/saveMenu")
    @ResponseBody
    public AjaxRes parentList(Menu menu) {
        AjaxRes ajaxRes = new AjaxRes();
        try{
            menuService.saveMenu(menu);
            ajaxRes.setMsg("保存成功");
            ajaxRes.setSuccess(true);
        }catch (Exception e){
            ajaxRes.setMsg("保存失败");
            ajaxRes.setSuccess(false);
        }
        //查询菜单
        return ajaxRes;
    }
    /*更新菜单*/
    @RequestMapping("/updateMenu")
    @ResponseBody
    public AjaxRes updateMenu(Menu menu){
        return menuService.updateMenu(menu);
    }


    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxRes deleteMenu(Long id){
        return menuService.deleteMenu(id);
    }

    /*获取树形菜单数据*/
    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData(){
        return menuService.getTreeData();
    }

}
