package cn.ghy.ps.project.service.menu;

import cn.ghy.ps.project.po.Permission;
import cn.ghy.ps.project.po.dto.MenuTree;
import cn.ghy.ps.project.po.dto.PermTree;

import java.util.List;

/**
 * @author 穹鏡
 * @date 2021/9/16 16:50
 * @描述
 */
public interface IPermissionService {

    int deleteByPrimaryKeys(Integer[] permissionId) throws Exception;

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    String checkMenuNameUnique(Permission permission);

    /**
     *
     * @描述 返回集合
     *
     * @date 2021/9/17 22:24
     */
    List<Permission> selectPersissionList(Permission record);

    List<MenuTree> selectMenuTree(String uid);

    /**
     *  查询权限结构
     */
    List<PermTree> selectPermTree();
}
