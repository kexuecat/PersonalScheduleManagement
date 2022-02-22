package cn.ghy.ps.project.mapper;

import cn.ghy.ps.project.po.User;

import java.util.List;
import java.util.Map;

public interface UserMapper{


    /**
     * 添加
     */
    int insertSelective(User record);

    /**
     * 根据Id主键查询单个用户 登录用此方法
     */
    User selectByPrimaryKey(String uId);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 将数据封装到Map类型中
     */
    Map<String, Object> queryInfoByUsername(String username);

    /**
     * 插入一条数据
     * @param data Map中包含id,username,password
     */
    void insertSelective(Map<String, String> data);

    /**
     * 更改用户
     */
    int updateByPrimaryKeySelective(User record);


    /*
     *
     * 批量删除用户
     */
    int deleteByPrimaryKeys(String[] ids);

    /*
     *
     * 用户列表 以及 根据条件查询
     */
    List<User> selectByUser(User user);

    /**
     *
     * @描述: login登录验证
     *
     * @params:
     * @return:
     * @date: 2021/9/29 21:50
     */
    User login(String loginName);


    /**
     * 校验手机号码是否唯一
     *
     * @param tel 手机号码
     *
     * @return 结果
     */
    User checkPhoneUnique(String tel);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     *
     * @return 结果
     */
    User checkEmailUnique(String email);


    /**
     * 校验用户登录名是否唯一
     *
     * @param email 用户邮箱
     *
     * @return 结果
     */
    User checkLoginNameUnique(String loginName);

    /** 查询最新的id*/
    Long selectUserMaxId();

}