package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class Score {
    @JsonIgnore
    private int id;

    @JsonIgnore
    private int study;

    private String exam;

    @JsonIgnore
    private Date createTime;

    private int score;
}
