package edu.qit.cloudclass.domain;

/**
 * Created by Root
 */

import lombok.Data;

@Data
public class Judgment {
    private String question;
    private boolean answer;
    private int score;
}
