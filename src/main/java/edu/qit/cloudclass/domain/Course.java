package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;

@Data
public class Course {
    private String id;

    private String name;

    private String image;

    private String teacher;

    private String tag;

    @JsonIgnore
    private Date createTime;
}
