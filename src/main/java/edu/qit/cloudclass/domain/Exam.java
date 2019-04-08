package edu.qit.cloudclass.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Exam {
    private String id;
    private String name;
    private Date createTime;
    private Date startTime;
    private Date stopTime;
    private int duration;
}
