package cn.ghy.ps.project.service.note;

import cn.ghy.ps.project.mapper.NoteMapper;
import cn.ghy.ps.project.po.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 穹鏡
 */
@Service
@Transactional
public class NoteServiceImpl implements INoteService{
    @Autowired
    NoteMapper noteMapper;


    /**
     *
     * @描述:  批量删除
     *
     * @params:
     * @return:
     * @date: 2021/9/30 14:44
    */
    @Override
    public int deleteByPrimaryKeys(Integer[] id)
    {
        return noteMapper.deleteByPrimaryKeys(id);
    }

    /**
     *
     * @描述:  添加
     *
     * @params:
     * @return:
     * @date: 2021/9/30 14:44
    */
    @Override
    public int insertSelective(Note record)
    {
        return noteMapper.insertSelective(record);
    }


    /**
     *
     * @描述:  主键查询
     *
     * @params:
     * @return:
     * @date: 2021/9/30 14:44
    */
    @Override
    public Note selectByPrimaryKey(Integer id)
    {
        return noteMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述:  修改
     *
     * @params:
     * @return:
     * @date: 2021/9/30 14:45
    */
    @Override
    public int updateByPrimaryKeySelective(Note record)
    {
        return noteMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述:  个人日志列表
     *
     * @params:
     * @return:
     * @date: 2021/9/30 14:50
    */
    @Override
    public List<Note> selectNoteList(Note note)
    {
        return noteMapper.selectNoteList(note);
    }
}
