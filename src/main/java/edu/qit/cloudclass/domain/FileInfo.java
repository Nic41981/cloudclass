package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class FileInfo {
    private String id;

    @JsonIgnore
    private String realName;

    private String suffix;

    private Date uploadTime;
}