package edu.qit.cloudclass.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class Study {
    private int id;
    private String student;
    private String no;
    private String course;
    private Date createTime;
}
