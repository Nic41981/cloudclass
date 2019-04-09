package edu.qit.cloudclass.domain.complex;

import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 */
@Getter
@Setter
public class ExamComplex extends Exam {
    private List<Question> choiceList;
    private List<Question> judgementList;

    public static ExamComplex getInstanceFromEXam(Exam exam){
        ExamComplex examComplex = new ExamComplex();
        examComplex.setId(exam.getId());
        examComplex.setCourse(exam.getCourse());
        examComplex.setName(exam.getName());
        examComplex.setStartTime(exam.getStartTime());
        examComplex.setStopTime(exam.getStopTime());
        examComplex.setDuration(exam.getDuration());
        examComplex.setCreateTime(exam.getCreateTime());
        examComplex.setChoiceList(null);
        examComplex.setJudgementList(null);
        return examComplex;
    }
}
