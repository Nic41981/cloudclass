package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Data
public class Question {
    private int id;
    private String exam;
    private String type;
    private String question;
    private int score;
    @JsonIgnore
    private String options;
    private List<String> optionList;
    private String answer;
}
