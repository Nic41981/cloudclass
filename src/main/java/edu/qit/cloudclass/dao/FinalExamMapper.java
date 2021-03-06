package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.FinalExam;
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
public interface FinalExamMapper {
    int insert(@Param("exam") FinalExam exam);

    int delete(@Param("id") String id);

    FinalExam findExamByPrimaryKey(@Param("id") String id);

    ExamSpinner findExamSpinnerByCourse(@Param("course") String course);

    int checkExamExist(@Param("id") String id);
}
