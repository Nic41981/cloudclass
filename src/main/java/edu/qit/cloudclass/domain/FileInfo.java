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
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_IMAGE = "image";

    private String id;

    @JsonIgnore
    private String realName;

    private String type;

    private Date uploadTime;
}
