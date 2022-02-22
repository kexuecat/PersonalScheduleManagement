package cn.ghy.ps.project.service.tel;

import cn.ghy.ps.project.po.Tel;

import java.util.List;

/**
 * @author 穹鏡
 */
public interface ITelService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Tel record);

    Tel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tel record);

    List<Tel> selectTelList(Tel tel);
}
