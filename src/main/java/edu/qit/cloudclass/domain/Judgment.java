package edu.qit.cloudclass.domain;

/**
 * Created by Root
 */

import lombok.Data;

@Data
public class Judgment {
    private int id;
    private String exam;
    private String question;
    private int score;
    private int answer;
}
