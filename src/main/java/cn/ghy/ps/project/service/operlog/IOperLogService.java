package cn.ghy.ps.project.service.operlog;

import cn.ghy.ps.project.po.OperLog;

import java.util.List;

/**
 * @author 穹鏡
 */
public interface IOperLogService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(OperLog record);

    OperLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperLog record);

    List<OperLog> selectOperLogList(OperLog operLog);

}
