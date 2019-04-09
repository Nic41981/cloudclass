package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Exam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface ExamMapper {
    int insert(@Param("exam") Exam exam);

    int delete(@Param("id")String id);

    Exam findExamByPrimaryKey(@Param("id") String id);
}
