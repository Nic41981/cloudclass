package edu.qit.cloudclass.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Course {
    private String id;
    private String name;
    private String image;
    private Date createTime;
    private String teacher;
    private String tag;
}
