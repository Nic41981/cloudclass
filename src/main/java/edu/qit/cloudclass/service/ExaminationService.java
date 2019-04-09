package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface ExaminationService {
    ServerResponse createFinalExam(ExamComplex exam,String courseId);

    ServerResponse createChapterExam(ExamComplex exam,String chapterId);

    ServerResponse getExamPage(String examId);

    ServerResponse examCheck(ExamComplex exam);

    ServerResponse associateDelete(String examId);
}
