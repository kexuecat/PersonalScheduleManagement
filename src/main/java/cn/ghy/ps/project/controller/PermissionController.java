package cn.ghy.ps.project.controller;

import cn.ghy.ps.common.constant.CsEnum;
import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Permission;
import cn.ghy.ps.project.po.dto.PermTree;
import cn.ghy.ps.project.service.menu.IPermissionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author 穹鏡
 */
@Controller
@RequestMapping("/menu")
public class PermissionController extends BaseController {
    private String prefix = "system/menu/";

    @Autowired
    IPermissionService iPermissionService;

    /**
     *
     * @描述 ajax请求获得 数据 treeData,添加角色时候需用
     *
     * @date 2021/9/16 17:19
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<PermTree> list()
    {
        return iPermissionService.selectPermTree();
    }


    /**
     *
     * @描述 页面跳转
     *
     * @date 2021/9/16 10:59
     */
    @RequestMapping("/tolist")
    @RequiresPermissions("menu:list")
    public String tolist()
    {
        return prefix + "menu";
    }


    /**
     *
     * @描述 表格列表
     *
     * @date 2021/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public List<Permission> listPag(Permission permission)
    {
        return iPermissionService.selectPersissionList(permission);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2021/9/16 11:37
     */
    @RequestMapping("/toAdd/{parentId}")
    @RequiresPermissions("menu:add")
    public String toAdd(@PathVariable("parentId") Integer pId, Model model)
    {
        Permission permission = null;
        if (CsEnum.menu.MENU_PID.getValue() != pId)
        {
            permission = iPermissionService.selectByPrimaryKey(pId);
        }
        else
        {
            permission = new Permission();
            permission.setPermissionId(CsEnum.menu.MENU_PID.getValue());
            permission.setPerName("主目录");
        }
        model.addAttribute("menu", permission);
        return prefix + "add";
    }


    /**
     *
     * @描述 添加菜单时候，返回tree页面 选择父级菜单
     *
     * @date 2021/9/17 22:34
     */
    @RequestMapping("/selectMenuTree")
    public String tree()
    {
        return prefix + "tree";
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2021/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("menu:del")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        try
        {
            iPermissionService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 添加保存
     *
     * @date 2021/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("menu:add")
    @ResponseBody
    public AjaxResult addRole(Permission permission)
    {
        permission.setCreateTime(new Date());

        //添加按钮需要添加父级菜单
        if (permission.getType() ==
                CsEnum.menu.MENU_TYPE_THREE.getValue() && permission.getParenId() == CsEnum.menu.MENU_PID.getValue())
        {
            return error("请选择父级菜单！");
        }


        int insert = 0;
        try
        {
            insert = iPermissionService.insertSelective(permission);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(insert);
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2021/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("menu:update")
    public String edit(@PathVariable("id") Integer id, Model model)
    {
        Permission permission = iPermissionService.selectByPrimaryKey(id);

        model.addAttribute("permission", permission);
        return prefix + "edit";
    }


    /**
     *
     * @描述 修改保存
     *
     * @date 2021/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("menu:update")
    @ResponseBody
    public AjaxResult save(Permission permission)
    {
        int i = 0;
        try
        {
            i = iPermissionService.updateByPrimaryKeySelective(permission);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        //更新session
        updateMenuSession(iPermissionService);
        return result(i);
    }


    /**
     * 校验名称唯一
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(Permission permission)
    {
        String uniqueFlag = "0";
        if (permission != null)
        {
            uniqueFlag = iPermissionService.checkMenuNameUnique(permission);
        }
        return uniqueFlag;
    }

}
