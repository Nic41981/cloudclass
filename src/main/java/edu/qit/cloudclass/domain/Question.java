package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author nic
 * @version 1.0
 */
@Data
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question implements Comparable<Question> {
    public static final String CHOICE_QUESTION = "choice";
    public static final String JUDGEMENT_QUESTION = "judgement";

    private int id;

    @JsonIgnore
    private String exam;

    @JsonIgnore
    private String type;

    private String question;

    private int score;

    @JsonIgnore
    private String options;

    private List<String> optionList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String answer;

    public void encodeOption() {
        String opA = optionList.get(0);
        String opB = optionList.get(1);
        String opC = optionList.get(2);
        String opD = optionList.get(3);
        try {
            opA = URLEncoder.encode(opA, "UTF-8");
            opB = URLEncoder.encode(opB, "UTF-8");
            opC = URLEncoder.encode(opC, "UTF-8");
            opD = URLEncoder.encode(opD, "UTF-8");
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        options = opA + "<>" + opB + "<>" + opC + "<>" + opD;
    }

    public void decodeOption() {
        optionList = new LinkedList<>();
        List<String> tmpList = new ArrayList<>(Arrays.asList(options.split("<>")));
        try {
            for (String op : tmpList) {
                optionList.add(URLDecoder.decode(op, "UTF-8"));
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        Collections.shuffle(optionList);
    }

    @JsonIgnore
    public boolean isChoiceQuestion() {
        return optionList != null && optionList.size() == 4;
    }

    @JsonIgnore
    public boolean isJudgementQuestion() {
        return optionList == null && ("true".equals(answer) || "false".equals(answer));
    }

    @Override
    public int compareTo(Question o) {
        return this.id > o.id ? 1 : -1;
    }

}
