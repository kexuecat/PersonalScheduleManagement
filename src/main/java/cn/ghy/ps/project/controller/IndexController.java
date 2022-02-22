package cn.ghy.ps.project.controller;

import cn.ghy.ps.common.constant.Constants;
import cn.ghy.ps.common.utils.StringUtils;
import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.project.po.*;
import cn.ghy.ps.project.po.dto.MenuTree;
import cn.ghy.ps.project.service.menu.IPermissionService;
import cn.ghy.ps.project.service.note.INoteService;
import cn.ghy.ps.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 穹鏡
 */
@Controller
@RequestMapping("/oa")
public class IndexController extends BaseController {

    private final static String prefix = "main/";

    @Autowired
    IUserService iUserService;

    @Autowired
    INoteService iNoteService;

    @Autowired
    IPermissionService iPermissionService;


    /**
     * 未授权页面
     */
    @RequestMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }


    /**
     * 首页跳转 登录成功进入此页面，加载左侧菜单
     */
    @RequestMapping("/index")
    public String index(Model model, HttpSession session)
    {
        List<MenuTree> menuTreeList = (List<MenuTree>) session.getAttribute(Constants.MENU_SESSION);
        if (StringUtils.isEmpty(menuTreeList))
        {
            menuTreeList = iPermissionService.selectMenuTree(getUserId());
            session.setAttribute(Constants.MENU_SESSION,menuTreeList);
        }
        model.addAttribute("menus", menuTreeList);

        model.addAttribute("user", getUser());
        return "index";
    }


    /**
     *
     * @描述: 首页 日志
     *
     * @params:
     * @return:
     * @date: 2021/9/30 10:53
     */
    @RequestMapping("/main")
    public String toMain(Model model)
    {
        //日志列表
        Note note = new Note();
        note.setCreateBy(getUserId());
        List<Note> notes = iNoteService.selectNoteList(note);

        model.addAttribute("notes", notes);

        return "main";
    }


}
