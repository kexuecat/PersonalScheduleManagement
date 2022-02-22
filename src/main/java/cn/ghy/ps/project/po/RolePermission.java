package cn.ghy.ps.project.po;

import cn.ghy.ps.framework.web.po.BasePo;

public class RolePermission extends BasePo{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.role_Id
     *
     * @mbggenerated
     */
    private Integer role_Id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_role_permission.permission_Id
     *
     * @mbggenerated
     */
    private Integer permission_Id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.id
     *
     * @return the value of t_role_permission.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.id
     *
     * @param id the value for t_role_permission.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.role_Id
     *
     * @return the value of t_role_permission.role_Id
     *
     * @mbggenerated
     */
    public Integer getRole_Id() {
        return role_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.role_Id
     *
     * @param role_Id the value for t_role_permission.role_Id
     *
     * @mbggenerated
     */
    public void setRole_Id(Integer role_Id) {
        this.role_Id = role_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_role_permission.permission_Id
     *
     * @return the value of t_role_permission.permission_Id
     *
     * @mbggenerated
     */
    public Integer getPermission_Id() {
        return permission_Id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_role_permission.permission_Id
     *
     * @param permission_Id the value for t_role_permission.permission_Id
     *
     * @mbggenerated
     */
    public void setPermission_Id(Integer permission_Id) {
        this.permission_Id = permission_Id;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RolePermission{");
        sb.append("id=").append(id);
        sb.append(", role_Id=").append(role_Id);
        sb.append(", permission_Id=").append(permission_Id);
        sb.append('}');
        return sb.toString();
    }
}