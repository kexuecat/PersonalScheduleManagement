package cn.ghy.ps.project.controller;

import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.page.TableDataInfo;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Note;
import cn.ghy.ps.project.service.note.INoteService;
import cn.ghy.ps.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author 穹鏡
 */
@Controller
@RequestMapping("/note")
public class NoteController extends BaseController {
    private final static String prefix = "system/note";

    @Autowired
    INoteService iNoteService;

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
    @RequiresPermissions("note:list")
    public String toList(Model model)
    {
        return prefix + "/note";
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
    public TableDataInfo tableList(Note note)
    {
        startPage();
        note.setCreateBy(getUserId());
        List<Note> notes = iNoteService.selectNoteList(note);
        return getDataTable(notes);

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
    @RequiresPermissions("note:add")
    @ResponseBody
    public AjaxResult addSave(Note note) throws Exception
    {
        note.setCreateBy(getUserId());
        note.setCreateTime(new Date());
        return result(iNoteService.insertSelective(note));
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
    @RequiresPermissions("note:del")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(iNoteService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("note:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Note note = iNoteService.selectByPrimaryKey(id);
        model.addAttribute("note", note);
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
    @RequiresPermissions("note:update")
    @ResponseBody
    public AjaxResult editSave(Note note)
    {
        return result(iNoteService.updateByPrimaryKeySelective(note));
    }
}
