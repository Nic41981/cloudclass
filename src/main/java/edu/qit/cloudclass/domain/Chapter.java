package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;

@Data
public class Chapter {
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String course;

    private int num;

    private String name;

    private String info;

    private String video;

    private String test;

    @JsonIgnore
    private Date createTime;

}