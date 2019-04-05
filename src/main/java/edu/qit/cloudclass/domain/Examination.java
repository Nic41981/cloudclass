package edu.qit.cloudclass.domain;

/**
 * Created by Root
 */

import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class Examination {

    public String type;
    public String target;
    public DateTimeFormatter startTime;
    public DateTimeFormatter stopTime;
    public int timeLength;
    public List<Choice> choiceList;
    public List<Judgment> judgmentList;
}
