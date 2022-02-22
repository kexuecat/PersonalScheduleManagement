package cn.ghy.ps.project.service.position;

import cn.ghy.ps.common.constant.CsEnum;
import cn.ghy.ps.common.utils.StringUtils;
import cn.ghy.ps.project.mapper.PositionMapper;
import cn.ghy.ps.project.mapper.UserMapper;
import cn.ghy.ps.project.po.Position;
import cn.ghy.ps.project.po.User;
import cn.ghy.ps.project.service.grop.GropServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 穹鏡
 */
@Service
@Transactional
public class PositionServiceImpl implements IPositionService{
    private Logger log= LoggerFactory.getLogger(GropServiceImpl.class);
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    UserMapper userMapper;

    /**
     *
     * @描述 批量删除
     *
     * @date 2021/9/18 19:44
     */
    @Override
    public int deleteByPrimarysKey(Integer[] positionId)
    {
        // 当前权限是否已经分配
        for (Integer id :
                positionId)
        {
            User user = new User();
            user.setPosition(id);
            List<User> users = userMapper.selectByUser(user);
            if (users.size()>0)
            {
                throw new RuntimeException("权限已分配，无法删除！");
            }
        }

        try
        {
            return positionMapper.deleteByPrimaryKeys(positionId);
        }
        catch (Exception e)
        {
            log.error("$$$$$ 删除权限失败=[{}]",e);
            throw new RuntimeException("操作失败！");
        }


    }


    /**
     *
     * @描述 添加
     *
     * @date 2021/9/18 21:47
     */
    @Override
    public int insertSelective(Position record)
    {
        return positionMapper.insertSelective(record);
    }

    /**
     *
     * @描述  查询根据id
     *
     * @date 2021/9/18 21:47
     */
    @Override
    public Position selectByPrimaryKey(Integer positionId)
    {
        return positionMapper.selectByPrimaryKey(positionId);
    }


    /**
     *
     * @描述  更改
     *
     * @date 2021/9/18 21:47
     */
    @Override
    public int updateByPrimaryKeySelective(Position record)
    {
        return positionMapper.updateByPrimaryKeySelective(record);
    }


    /**
     *
     * @描述 列表
     *
     * @date 2021/9/15 14:35
     */
    @Override
    public List<Position> selectPositionList(Position position)
    {
        return positionMapper.selectPositionList(position);
    }

    /**
     *
     * @描述 校验是否唯一
     *
     * @date 2021/9/18 19:52
     */
    @Override
    public String checkPositionNameUnique(Position position)
    {
        Integer postId = StringUtils.isNull(position.getPositionId()) ? -1 : position.getPositionId();
        Position info = positionMapper.checkPositionNameUnique(position.getPositionName());
        if (StringUtils.isNotNull(info) && !info.getPositionId().equals(postId))
        {
            return CsEnum.unique.NOT_UNIQUE.getValue();
        }
        return CsEnum.unique.IS_UNIQUE.getValue();
    }
}
