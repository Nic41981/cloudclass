package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.ChapterExam;
import edu.qit.cloudclass.domain.spinner.ExamSpinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@Mapper
public interface ChapterExamMapper {
    int insert(@Param("exam") ChapterExam exam);
    ChapterExam findExamByPrimaryKey(@Param("id")String id);
    int delete(@Param("id") String id);
    List<ExamSpinner> selectExamSpinnerListByCourse(@Param("course") String course);
    int checkExamExist(@Param("id") String id);
}
