package edu.qit.cloudclass.domain;

/**
 * Created by Root
 */
import lombok.Data;

@Data
public class Choice {
    private int id;
    private String exam;
    private String question;
    private int score;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
}
