package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.spinner.ExamSpinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface ChapterExamMapper {
    List<ExamSpinner> getExamSpinnerList(@Param("course") String course);
}
