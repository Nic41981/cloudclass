package edu.qit.cloudclass.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author nic
 * @version 1.0
 */
@Getter
@Setter
public class Answer implements Comparable<Answer> {
    private int id;
    private int score;
    private String answer;

    @Override
    public int compareTo(Answer o) {
        return this.id > o.id ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return id == answer.id;
    }
}
