package edu.qit.cloudclass.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

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
