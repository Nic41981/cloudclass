package edu.qit.cloudclass.domain.complex;

import edu.qit.cloudclass.domain.Question;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class ExamComplex {
    private String id;
    private String name;
    private Date createTime;
    private Date startTime;
    private Date stopTime;
    private int duration;
    private List<Question> choiceList;
    private List<Question> judgementList;
}
