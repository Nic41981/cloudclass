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
    @JsonIgnore
    public static final String IMAGE_FILE_TYPE = "image";
    @JsonIgnore
    public static final String VIDEO_FILE_TYPE = "video";

    private String id;

    @JsonIgnore
    private String filePath;

    private String mappingPath;

    private String realName;

    private String suffix;

    @JsonIgnore
    private Date uploadTime;


}
