package cn.ghy.ps.project.service.schedule;

import cn.ghy.ps.project.po.Schedule;

import java.util.List;

/**
 * @author 穹鏡
 */
public interface IScheduleService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Schedule record, String[] userIds);

    Schedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Schedule record,String[] userIds);

    List<Schedule> selectScheduleList(Schedule schedule);

    int updateComplete(Schedule schedule);

    int updateTimeComplete(Schedule schedule);
}
