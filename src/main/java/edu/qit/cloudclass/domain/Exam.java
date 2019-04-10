package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

@Data
public class Exam {
    @JsonIgnore
    protected String id;
    protected String course;
    protected String name;
    @JsonIgnore
    protected Date createTime;
    protected Date startTime;
    protected Date stopTime;
    protected int duration;

    public boolean isSubmittable(){
        Date now = new Date();
        return now.after(this.startTime) && now.before(this.stopTime);
    }
}
