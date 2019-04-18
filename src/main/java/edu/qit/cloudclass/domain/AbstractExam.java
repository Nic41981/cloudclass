package edu.qit.cloudclass.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-18
 */
@Getter
@Setter
public abstract class AbstractExam {
    protected String id;
    protected String name;
    protected Date createTime;
    protected List<Question> choiceList;
    protected List<Question> judgementList;

    protected abstract boolean isCompleteExamination();
}
