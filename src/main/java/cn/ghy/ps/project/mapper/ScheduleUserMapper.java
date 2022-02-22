package cn.ghy.ps.project.mapper;

import cn.ghy.ps.project.po.ScheduleUser;

import java.util.List;

public interface ScheduleUserMapper {
    int deleteByPrimaryKeys(Integer[] id);


    int insertSelective(List<ScheduleUser> userList);

}