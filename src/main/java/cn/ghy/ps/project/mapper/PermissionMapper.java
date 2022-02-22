package cn.ghy.ps.project.mapper;

import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.page.TableDataInfo;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Tel;
import cn.ghy.ps.project.po.dto.MenuTree;
import cn.ghy.ps.project.po.dto.PermTree;
import cn.ghy.ps.project.po.Permission;
import cn.ghy.ps.project.service.tel.ITelService;
import cn.ghy.ps.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface PermissionMapper {

    /**
     * 删除
     * @param permissionId
     * @return
     */
    int deleteByPrimaryKeys(Integer[] permissionId);


    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Permission record);

    /**
     * 主键查询
     * @param permissionId
     * @return
     */
    Permission selectByPrimaryKey(Integer permissionId);

    /**
     *  菜单名唯一性查询
     * @param permission
     * @return
     */
    Permission checkMenuNameUnique(Permission permission);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * 列表
     * @param record
     * @return
     */
    List<Permission> selectPersissionList(Permission record);

    /**
     *  查询权限结构
     */
    List<PermTree> selectPermTree();


    /**
     * selectMenuTree 菜单列表结构 根据用户Id查询出左侧菜单列表
     */
    List<MenuTree> selectMenuTree(String uid);

    /**
     * @author 穹鏡
     */
    @Controller
    @RequestMapping("/tel")
    class TelController extends BaseController {
        private final static String prefix = "system/tel";

        @Autowired
        ITelService iTelService;

        @Autowired
        IUserService iUserService;

        /**
         *
         * @描述: 跳转到列表页
         *
         * @params:
         * @return:
         * @date: 2021/9/26 21:13
         */
        @RequestMapping("/tolist")
        public String toList(Model model)
        {
            return prefix + "/tel";
        }



        /**
         *
         * @描述: 返回表格数据
         *
         * @params:
         * @return:
         * @date: 2021/9/26 21:15
         */
        @RequestMapping("/tableList")
        @ResponseBody
        public TableDataInfo tableList(Tel tel)
        {
            startPage();
            List<Tel> tels = iTelService.selectTelList(tel);
            return getDataTable(tels);

        }


        /**
         *
         * @描述: 添加页面
         *
         * @params:
         * @return:
         * @date: 2021/9/26 21:15
         */
        @RequestMapping("/toAdd")
        public String toAdd(Model model)
        {
            return prefix + "/add";
        }

        /**
         *
         * @描述: 添加保存
         *
         * @params:
         * @return:
         * @date: 2021/9/26 21:16
         */
        @RequestMapping("/addSave")
        @ResponseBody
        public AjaxResult addSave(Tel tel) throws Exception
        {
            return result(iTelService.insertSelective(tel));
        }

        /**
         *
         * @描述: 删除
         *
         * @params:
         * @return:
         * @date: 2021/9/27 22:02
         */
        @RequestMapping("/del")
        @ResponseBody
        public AjaxResult del(Integer[] ids)
        {
            return result(iTelService.deleteByPrimaryKeys(ids));
        }


        /**
         *
         * @描述: 编辑页面
         *
         * @params:
         * @return:
         * @date: 2021/9/26 21:17
         */
        @RequestMapping("/edit/{id}")
        public String toEdit(@PathVariable("id") Integer id, Model model)
        {
            Tel tel = iTelService.selectByPrimaryKey(id);
            model.addAttribute("tel", tel);
            return prefix + "/edit";
        }


        /**
         *
         * @描述: 修改保存
         *
         * @params:
         * @return:
         * @date: 2021/9/27 21:01
         */
        @RequestMapping("/editSave")
        @ResponseBody
        public AjaxResult editSave(Tel tel)
        {
            return result(iTelService.updateByPrimaryKeySelective(tel));
        }
    }
}