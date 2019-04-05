package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.ExamSpinner;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
public interface ExamMapper {
    /**
     * 王恺
     */
    List<ExamSpinner> getExaminationList(String courseId);
}
