package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data//该注解会自动生成 get set toString 方法

public class TCourse {
    private String id;
    private String name;
    private String image;

//    @CreatedDate
    private Date createTime;
    private String teacher;
    private String tag;

    public TCourse() {
    }

    public TCourse(String id, String name, String image, Date createTime, String teacher, String tag) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.createTime = createTime;
        this.teacher = teacher;
        this.tag = tag;
    }
}
