package cn.ghy.ps.project.controller;

import cn.ghy.ps.common.constant.CsEnum;
import cn.ghy.ps.common.utils.DateUtils;
import cn.ghy.ps.framework.web.controller.BaseController;
import cn.ghy.ps.framework.web.page.TableDataInfo;
import cn.ghy.ps.framework.web.po.AjaxResult;
import cn.ghy.ps.project.po.Schedule;
import cn.ghy.ps.project.service.schedule.IScheduleService;
import cn.ghy.ps.project.service.user.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 穹鏡
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {
    private final static String prefix = "system/schedule";

    @Autowired
    IScheduleService iScheduleService;

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
    @RequiresPermissions("schedule:list")
    public String toList()
    {
        return prefix + "/schedule";
    }


    /**
     *
     * @描述: 修改日程工作完成状态
     *
     * @params:
     * @return:
     * @date: 2021/9/29 14:00
     */
    @RequestMapping("/updateComplete")
    @RequiresPermissions("schedule:update")
    @ResponseBody
    public AjaxResult updateComplete(Schedule schedule)
    {
        schedule.setIsComplete(CsEnum.scheduled.SCHEDULE_YES_COMPLETE.getValue());
        return result(iScheduleService.updateComplete(schedule));
    }

    /**
     *
     * @描述: 延期日程
     *
     * @params:
     * @return:
     * @date: 2021/9/29 14:00
     */
    @RequestMapping("/updateTimeComplete")
    @RequiresPermissions("schedule:update")
    @ResponseBody
    public AjaxResult updateTimeComplete(Schedule schedule)
    {
        Date date = new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,7); //把日期往后增加一周,整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一周的结果
        schedule.setEndTime(date);
        return result(iScheduleService.updateTimeComplete(schedule));
    }

    /**
     *
     * @描述: ajaxgetList
     *
     * @params:
     * @return:
     * @date: 2021/9/29 10:09
     */
    @RequestMapping("/ajaxgetMap")
    @ResponseBody
    public Map<String, String> ajaxgetMap()
    {
        Map<String, String> map = new HashMap<>();
        List<Schedule> schedules = iScheduleService.selectScheduleList(new Schedule());
        for (Schedule s : schedules)
        {
            if (s.getIsComplete() == CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue())
            {
                map.put(DateUtils.DateToSTr(s.getStartTime()).substring(0, 10), s.getTitle()+"(开始)");
                map.put(DateUtils.DateToSTr(s.getEndTime()).substring(0, 10), s.getTitle()+"(结束)");
            }
        }
        return map;
    }


    /**
     *
     * @描述: 通过安排日期模糊查询出当天的安排
     *     返回多个数据
     * @params:
     * @return:
     * @date: 2021/9/29 11:21
     */
    @RequestMapping("/editMore/{date}")
    @RequiresPermissions("schedule:update")
    public String editMore(@PathVariable("date") String date, Model model)
    {
        Schedule schedule = new Schedule();
        //查询未完成的
        schedule.setIsComplete(CsEnum.scheduled.SCHEDULE_NO_COMPLETE.getValue());
        schedule.setStartTime(DateUtils.StrToDate(date));
        System.out.println(DateUtils.DateToSTr(DateUtils.StrToDate(date)));
        List<Schedule> schedules = iScheduleService.selectScheduleList(schedule);
        model.addAttribute("schedules", schedules);
        return prefix + "/editMore";
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
    public TableDataInfo tableList(Schedule schedule)
    {
        startPage();
        schedule.setCreateBy(getUserId());
        List<Schedule> schedules = iScheduleService.selectScheduleList(schedule);
        return getDataTable(schedules);
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
    @RequiresPermissions("schedule:add")
    public String toAdd()
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
    @RequiresPermissions("schedule:add")
    @ResponseBody
    public AjaxResult addSave(Schedule schedule, String[] userIds) throws Exception
    {
        schedule.setCreateBy(getUserId());
        schedule.setCreateTime(new Date());
        return result(iScheduleService.insertSelective(schedule, userIds));
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
    @RequiresPermissions("schedule:del")
    @ResponseBody
    public AjaxResult del(Integer[] ids)
    {
        return result(iScheduleService.deleteByPrimaryKeys(ids));
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
    @RequiresPermissions("schedule:update")
    public String toEdit(@PathVariable("id") Integer id, Model model)
    {
        Schedule schedule = iScheduleService.selectByPrimaryKey(id);
        System.out.println(schedule);
        model.addAttribute("schedule", schedule);
        return prefix + "/edit";
    }


    /**
     *
     * @描述: 通过id获取单条
     *
     * @params:
     * @return:
     * @date: 2021/9/30 23:42
     */
    @RequestMapping("/selectById/{id}")
    @ResponseBody
    public Schedule selectById(@PathVariable("id") Integer id)
    {
        return iScheduleService.selectByPrimaryKey(id);
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
    @RequiresPermissions("schedule:update")
    @ResponseBody
    public AjaxResult editSave(Schedule schedule, String[] userIds)
    {
        return result(iScheduleService.updateByPrimaryKeySelective(schedule, userIds));
    }
}
