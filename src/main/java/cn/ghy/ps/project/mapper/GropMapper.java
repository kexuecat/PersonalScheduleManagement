package cn.ghy.ps.project.mapper;

import cn.ghy.ps.project.po.Grop;

import java.util.List;

public interface GropMapper {
    /**
     *
     * 批量删除
     */
    int deleteByPrimaryKeys(String[] ids) throws Exception;


    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Grop record);

    /**
     * 主键查询
     * @param depId
     * @return
     */
    Grop selectByPrimaryKey(String depId);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Grop record);


    /**
     *
     * @描述 分组列表
     *
     * @date 2021/9/15 13:39
     */
    List<Grop> selectGropList(Grop grop);

     /**
      *
      * @描述 检验名称唯一性
      *
      * @date 2021/9/16 11:42
      */
     Grop checkGropNameUnique(String gropName);

     /**
      *
      * @描述: 查询所有分组下的所有用户 用户归类 树状数据
      *
      * @date: 2021/9/27 11:25
     */
    List<Grop> selectGropAndUser();
}