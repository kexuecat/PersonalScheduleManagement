package cn.ghy.ps.project.po;

import cn.ghy.ps.framework.web.po.BasePo;

import java.util.Date;
import java.util.List;

public class Grop extends BasePo{

    private Integer depId;
    private String leader;

    private Date createTime;


    private String gropName;

    private String introduce;

    private Integer status;

    private List<User> users;


    public Integer getDepId()
    {
        return depId;
    }

    public void setDepId(Integer depId)
    {
        this.depId = depId;
    }

    public String getLeader()
    {
        return leader;
    }

    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getGropName()
    {
        return gropName;
    }

    public void setGropName(String gropName)
    {
        this.gropName = gropName;
    }

    public String getIntroduce()
    {
        return introduce;
    }

    public void setIntroduce(String introduce)
    {
        this.introduce = introduce;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers(List<User> users)
    {
        this.users = users;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("Grop{");
        sb.append("depId=").append(depId);
        sb.append(", leader='").append(leader).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", gropName='").append(gropName).append('\'');
        sb.append(", introduce='").append(introduce).append('\'');
        sb.append(", status=").append(status);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}