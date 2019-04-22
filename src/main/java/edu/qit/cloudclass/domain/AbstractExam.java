package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    protected String id;

    protected String name;

    @JsonIgnore
    protected Date createTime;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected int choiceScoreWeight;

    protected List<Question> choiceList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected int judgementScoreWeight;

    protected List<Question> judgementList;

    @JsonIgnore
    protected abstract boolean isCompleteExamination();
}
