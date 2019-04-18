package edu.qit.cloudclass.domain;

import edu.qit.cloudclass.tool.Tool;
import lombok.Getter;
import lombok.Setter;


/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@Getter
@Setter
public class ChapterExam extends AbstractExam {
    private String chapter;

    @Override
    public boolean isCompleteExamination() {
        return Tool.checkParamsNotNull(name,chapter);
    }
}
