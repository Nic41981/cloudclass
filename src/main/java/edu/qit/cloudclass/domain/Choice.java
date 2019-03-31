package edu.qit.cloudclass.domain;

/**
 * Created by Root
 */
import lombok.Data;

@Data
public class Choice {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private int score;


}
