package cn.ghy.ps.project.service.note;

import cn.ghy.ps.project.po.Note;

import java.util.List;

/**
 * @author 穹鏡
 */
public interface INoteService{
    int deleteByPrimaryKeys(Integer[] id);

    int insertSelective(Note record);

    Note selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Note record);

    List<Note> selectNoteList(Note note);
}
