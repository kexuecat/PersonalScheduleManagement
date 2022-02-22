package cn.ghy.ps.project.service.grop;

import cn.ghy.ps.common.constant.CsEnum;
import cn.ghy.ps.common.utils.StringUtils;
import cn.ghy.ps.project.mapper.GropMapper;
import cn.ghy.ps.project.mapper.UserMapper;
import cn.ghy.ps.project.po.Grop;
import cn.ghy.ps.project.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 穹镜
 */
@Service
@Transactional
public class GropServiceImpl implements IGropService{

    private Logger log = LoggerFactory.getLogger(GropServiceImpl.class);
    @Autowired
    GropMapper gropMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * @描述 删除分组
     * @date 2021/9/16 13:12
     */

    @Override
    public int deleteByPrimaryKeys(String[] ids) throws Exception
    {
        User user = new User();
        for (String id : ids)
        {
            user.setGrop(Integer.parseInt(id));
            List<User> users = userMapper.selectByUser(user);
            if (users.size() > 0)
            {
                throw new Exception("分组存在用户，不允许删除！");
            }
        }
        try
        {
            return gropMapper.deleteByPrimaryKeys(ids);
        }
        catch (Exception e)
        {
            log.error("$$$$$ 删除分组失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }
    }


    /**
     *
     * @描述 添加
     *
     * @date 2021/9/18 22:17
     */
    @Override
    public int insertSelective(Grop record)
    {
        return gropMapper.insertSelective(record);
    }


    /**
     *
     * @描述 通过id
     *
     * @date 2021/9/16 14:39
     */
    @Override
    public Grop selectByPrimaryKey(String depId)
    {
        return gropMapper.selectByPrimaryKey(depId);
    }

    @Override
    public int updateByPrimaryKeySelective(Grop record) throws Exception
    {
        //如果当前分组下 有员工 无法删除

        if (record.getStatus() == 1) //停用分组
        {
            User user = new User();
            user.setGrop(record.getDepId());
            List<User> users = userMapper.selectByUser(user);
            if (users.size() > 0)
            {
                throw new Exception("分组已分配，不允许停用！");
            }
        }
        return gropMapper.updateByPrimaryKeySelective(record);
    }




    /**
     * @描述 分组列表
     * @date 2021/9/15 13:41
     */
    @Override
    public List<Grop> selectGropList(Grop grop)
    {
        return gropMapper.selectGropList(grop);
    }


    /**
     * 校验分组名称是否唯一
     *
     * @param grop 分组信息
     *
     * @return 结果
     */
    @Override
    public String checkGropNameUnique(Grop grop)
    {

        if (grop.getDepId() == null)
        {
            grop.setDepId(-1);
        }
        Integer gropId = grop.getDepId();
        Grop info = gropMapper.checkGropNameUnique(grop.getGropName());

//        判断查询出来的和传进来的是否相同
        if (StringUtils.isNotNull(info) && StringUtils.isNotNull(info.getDepId())
                && !info.getDepId().equals(gropId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }

    /**
     *
     * @描述: 查询所有分组下的所有用户 用户归类 树状数据
     *
     * @date: 2021/9/27 11:25
     */
    @Override
    public List<Grop> selectGropAndUser()
    {
        return gropMapper.selectGropAndUser();
    }
}
