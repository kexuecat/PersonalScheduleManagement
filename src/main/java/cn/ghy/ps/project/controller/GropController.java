package cn.ghy.ps.project.controller;

import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.page.TableDataInfo;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Grop;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.project.service.grop.IGropService;
import cn.ghy.ps.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author 穹镜
 */
@Controller
@RequestMapping("/grop")
public class GropController extends BaseController{

    private String prefix = "system/grop/";

    @Autowired
    IGropService iGropService;

    @Autowired
    IUserService iUserService;


    /**
     *
     * @描述 页面跳转到分组
     *
     * @date 2021/9/16 10:59
     */

    @RequestMapping("/tolist")
    @RequiresPermissions("grop:list")
    public String tolist()
    {
        return prefix + "grop";
    }


    /**
     *
     * @描述 ajax请求的所有分组
     *
     * @date 2021/9/16 10:48
     */
    @RequestMapping("/ajaxlist")
    @ResponseBody
    public List<Grop> list(Grop grop)
    {
        List<Grop> grops = iGropService.selectGropList(grop);
        return grops;
    }

    /**
     *
     * @描述 分组列表页
     *
     * @date 2021/9/16 10:52
     */
    @RequestMapping("/tableList")
    @ResponseBody
    public TableDataInfo listPag(Grop grop)
    {
        //开启分页
        startPage();
        List<Grop> grops = iGropService.selectGropList(grop);
        return getDataTable(grops);
    }


    /**
     *
     * @描述 新增页面
     *
     * @date 2021/9/16 11:37
     */
    @RequiresPermissions("grop:add")
    @RequestMapping("/toAdd")
    public String toAdd(Model model)
    {
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);
        return prefix + "add";
    }


    /**
     *
     * @描述: 查询所有分组下的所有用户 用户归类 树状数据
     *
     * @date: 2021/9/27 11:25
     */
    @RequestMapping("/getGropAndUserTreeData")
    @ResponseBody
    public List<Object> GropAndUserTreeData()
    {
        List<Grop> grops = iGropService.selectGropAndUser();

        List<User> users=new ArrayList<>();
        LinkedList<Object> gropList = new LinkedList<>();
        for (Grop grop : grops)
        {
            Map<String, Object> gropMap = new HashMap();
            gropMap.put("name", grop.getGropName());
            gropMap.put("id", null);

            users = grop.getUsers();

            LinkedList<Object> userlist = new LinkedList<>();
            for (User user : users)
            {
                Map<String, Object> userMap = new HashMap();
                userMap.put("name",user.getName());
                userMap.put("id",user.getUid());
                userMap.put("icon","/img/timg.jpg");
                userlist.add(userMap);
            }
            gropMap.put("children",userlist);
            gropList.add(gropMap);
        }

        return gropList;
    }


    /**
     *
     * @描述 批量删除
     *
     * @date 2021/9/16 11:53
     */
    @RequestMapping("/del")
    @RequiresPermissions("grop:del")
    @ResponseBody
    public AjaxResult del(String[] ids)
    {
        try
        {
            iGropService.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return success();
    }


    /**
     *
     * @描述 执行保存操作
     *
     * @date 2021/9/16 11:54
     */

    @RequestMapping("/addSave")
    @RequiresPermissions("grop:add")
    @ResponseBody
    public AjaxResult addGrop(Grop grop)
    {
        grop.setCreateTime(new Date());
        return  result(iGropService.insertSelective(grop));
    }


    /**
     *
     * @描述 编辑修改页面
     *
     * @date 2021/9/16 14:06
     */
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("grop:update")
    public String edit(@PathVariable("id") String id, Model model)
    {
        Grop grop = iGropService.selectByPrimaryKey(id);
        List<User> users = iUserService.selectByUser(new User());
        model.addAttribute("users", users);

        model.addAttribute("Grop", grop);
        return prefix + "edit";

    }

    /**
     *
     * @描述 修改保存
     *
     * @date 2021/9/16 16:12
     */
    @RequestMapping("/editSave")
    @RequiresPermissions("grop:update")
    @ResponseBody
    public AjaxResult save(Grop grop)
    {
        int i = 0;
        try
        {
            i = iGropService.updateByPrimaryKeySelective(grop);
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
        return result(i);
    }


    /**
     * 校验分组名称
     */
    @PostMapping("/checkGropNameUnique")
    @ResponseBody
    public String checkGropNameUnique(Grop grop)
    {
        String uniqueFlag = "0";
        if (grop != null)
        {
            uniqueFlag = iGropService.checkGropNameUnique(grop);
        }
        return uniqueFlag;
    }
}
