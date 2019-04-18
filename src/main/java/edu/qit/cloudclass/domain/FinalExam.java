package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.qit.cloudclass.tool.Tool;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@Getter
@Setter
public class FinalExam extends AbstractExam{
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String course;

    private Date startTime;

    private Date stopTime;

    @JsonIgnore
    private static final int MAX_EXAM_TIME = 24*60*60*1000;

    @JsonIgnore
    private static final int MIN_EXAM_TIME = 5*60*1000;

    @Override
    @JsonIgnore
    public boolean isCompleteExamination(){
        return Tool.checkParamsNotNull(name,course) && startTime != null && stopTime != null;
    }

    @JsonIgnore
    public boolean isExamTimeCorrect(){
        if (stopTime.getTime() - startTime.getTime() > MAX_EXAM_TIME){
            return false;
        }
        if (stopTime.getTime() - startTime.getTime() < MIN_EXAM_TIME){
            return false;
        }
        return new Date().before(startTime);
    }

    @JsonIgnore
    public boolean isSubmittable() {
        Date now = new Date();
        return now.after(this.startTime) && now.before(this.stopTime);
    }
}
