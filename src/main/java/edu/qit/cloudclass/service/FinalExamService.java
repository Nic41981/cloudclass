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
    ServerResponse create(FinalExam exam);
    ServerResponse page(String examId);
    ServerResponse submit(String userId,String examId,AnswerComplex answer);
    ServerResponse score(String userId, String examId);
    void associateDelete(String examId);
}
