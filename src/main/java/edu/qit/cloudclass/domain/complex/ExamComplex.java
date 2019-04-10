package edu.qit.cloudclass.domain.complex;

import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.Question;
import edu.qit.cloudclass.tool.Tool;
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

    public static ExamComplex getInstanceFromExam(Exam exam){
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

    public boolean isCompleteExamination(){
        if (Tool.checkParamsNotNull(this.name,this.course)){
            return false;
        }
        if (this.startTime == null || this.stopTime == null || this.duration == 0){
            return false;
        }
        if (this.choiceList == null || this.choiceList.isEmpty()){
            return false;
        }
        return this.judgementList != null && !this.judgementList.isEmpty();
    }

    public boolean isExamTimeCorrect(){
        if (stopTime.getTime() - startTime.getTime() < 24*60*60*1000){
            return false;
        }
        if (duration < 5){
            return false;
        }
        return startTime.getTime() + duration * 60 * 1000 <= stopTime.getTime();
    }
}
