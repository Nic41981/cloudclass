package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
@Data
public class Notice {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private String course;
    private String content;
    private Date createTime;
}
