package cn.ghy.ps.project.service.notice;

import cn.ghy.ps.project.po.Notice;
import cn.ghy.ps.project.po.Position;

import java.util.List;

/**
 * @author 穹镜
 */
public interface INoticeService{
    /**
     *
     * @描述 批量删除
     *
     * @date 2021/9/18 20:15
     */
    int deleteByPrimaryKeys(Integer[] positionId) throws Exception;

    /**
     *
     * @描述插入
     *
     * @date 2021/9/18 20:15
     */
    int insertSelective(Notice record);

    /**
     *
     * @描述 根据id查询
     *
     * @date 2021/9/18 20:15
     */
    Notice selectByPrimaryKey(Integer id);

    /**
     *
     * @描述 修改
     *
     * @date 2021/9/18 20:15
     */
    int updateByPrimaryKeySelective(Notice record);

    List<Notice> selectNoticeList(Notice record);

}
