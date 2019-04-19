package edu.qit.cloudclass.service;

import edu.qit.cloudclass.domain.ChapterExam;
import edu.qit.cloudclass.domain.complex.AnswerComplex;
import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-18
 */
public interface ChapterExamService {
    ServerResponse create(ChapterExam exam, String userId);
    ServerResponse page(String examId);
    ServerResponse submit(String userId,String examId,AnswerComplex answer);
    ServerResponse score(String userId, String examId);
    void associateDelete(String examId);

}
