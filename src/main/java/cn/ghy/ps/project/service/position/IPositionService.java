package cn.ghy.ps.project.service.position;

import cn.ghy.ps.project.po.Position;

import java.util.List;

/**
 * @author 穹鏡
 * @date 2021/9/15 14:34
 * @描述
 */
public interface IPositionService {
    /**
     *
     * @描述 根据主键删除
     *
     * @date 2021/9/18 19:32
     */
    int deleteByPrimarysKey(Integer[] positionId);


    /**
     *
     * @描述 插入
     *
     * @date 2021/9/18 19:32
     */
    int insertSelective(Position record);

    /**
     *
     * @描述  根据主键查询
     *
     * @date 2021/9/18 19:33
     */
    Position selectByPrimaryKey(Integer positionId);

    /**
     *
     * @描述 字段不为空更新
     *
     * @date 2021/9/18 19:33
     */
    int updateByPrimaryKeySelective(Position record);


    /**
     *
     * @描述 根据对象所有字段查询
     *
     * @date 2021/9/18 19:34
     */
    List<Position> selectPositionList(Position position);


    /**
     *
     * @描述 校验名称是否唯一
     *
     * @date 2021/9/18 19:51
     */
    String checkPositionNameUnique(Position position);
}
