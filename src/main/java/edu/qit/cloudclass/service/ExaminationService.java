package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.Answer;
import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.domain.complex.ExamComplex;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface ExaminationService {
    ServerResponse createFinalExam(ExamComplex exam,String userId);

    ServerResponse createChapterExam(ExamComplex exam,String chapterId,String userId);

    ServerResponse getExamPage(String examId);

    ServerResponse submitExam(AnswerComplex answer);

    ServerResponse finalExamScore(String userId,String courseId);

    ServerResponse chapterExamScore(String userId,String chapterId);

    ServerResponse associateDelete(String examId);
}
