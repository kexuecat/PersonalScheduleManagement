package cn.ghy.ps.project.mapper;

import cn.ghy.ps.project.po.Tel;

import java.util.List;

public interface TelMapper {
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Tel record);

    Tel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tel record);

    List<Tel> selectTelList(Tel tel);

}