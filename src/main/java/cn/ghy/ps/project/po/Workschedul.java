package cn.ghy.ps.project.po;

import cn.ghy.ps.framework.web.po.BasePo;

import java.util.Date;

public class Workschedul extends BasePo{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.w_userId
     *
     * @mbggenerated
     */
    private String w_userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.arranger
     *
     * @mbggenerated
     */
    private String arranger;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.Agent
     *
     * @mbggenerated
     */
    private String agent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.createTime
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.over_time
     *
     * @mbggenerated
     */
    private Date over_time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_workscheduling.w_Status
     *
     * @mbggenerated
     */
    private Integer w_Status;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.id
     *
     * @return the value of t_workscheduling.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.id
     *
     * @param id the value for t_workscheduling.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.w_userId
     *
     * @return the value of t_workscheduling.w_userId
     *
     * @mbggenerated
     */
    public String getW_userId() {
        return w_userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.w_userId
     *
     * @param w_userId the value for t_workscheduling.w_userId
     *
     * @mbggenerated
     */
    public void setW_userId(String w_userId) {
        this.w_userId = w_userId == null ? null : w_userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.arranger
     *
     * @return the value of t_workscheduling.arranger
     *
     * @mbggenerated
     */
    public String getArranger() {
        return arranger;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.arranger
     *
     * @param arranger the value for t_workscheduling.arranger
     *
     * @mbggenerated
     */
    public void setArranger(String arranger) {
        this.arranger = arranger == null ? null : arranger.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.Agent
     *
     * @return the value of t_workscheduling.Agent
     *
     * @mbggenerated
     */
    public String getAgent() {
        return agent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.Agent
     *
     * @param agent the value for t_workscheduling.Agent
     *
     * @mbggenerated
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.createTime
     *
     * @return the value of t_workscheduling.createTime
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.createTime
     *
     * @param createTime the value for t_workscheduling.createTime
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.over_time
     *
     * @return the value of t_workscheduling.over_time
     *
     * @mbggenerated
     */
    public Date getOver_time() {
        return over_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.over_time
     *
     * @param over_time the value for t_workscheduling.over_time
     *
     * @mbggenerated
     */
    public void setOver_time(Date over_time) {
        this.over_time = over_time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_workscheduling.w_Status
     *
     * @return the value of t_workscheduling.w_Status
     *
     * @mbggenerated
     */
    public Integer getW_Status() {
        return w_Status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_workscheduling.w_Status
     *
     * @param w_Status the value for t_workscheduling.w_Status
     *
     * @mbggenerated
     */
    public void setW_Status(Integer w_Status) {
        this.w_Status = w_Status;
    }
}