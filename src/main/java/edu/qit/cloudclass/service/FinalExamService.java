package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.FinalExam;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
public interface FinalExamService {
    ServerResponse create(FinalExam exam, String userId);
    ServerResponse page(String examId, String userId);
    ServerResponse submit(AnswerComplex answer);
    ServerResponse score(String userId, String courseId);
    void associateDelete(String examId);
}
