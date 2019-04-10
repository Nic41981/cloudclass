package edu.qit.cloudclass.domain.complex;

import edu.qit.cloudclass.domain.Answer;
import lombok.Data;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class AnswerComplex {
    private String user;
    private String exam;
    List<Answer> choiceList;
    List<Answer> judgementList;
}
