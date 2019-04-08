package edu.qit.cloudclass.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class Score {
    private int id;
    private String study;
    private String exam;
    private Date createTime;
    private int score;
}
