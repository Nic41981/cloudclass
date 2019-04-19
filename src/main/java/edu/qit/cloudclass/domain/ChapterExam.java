package edu.qit.cloudclass.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.qit.cloudclass.tool.Tool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author nic
 * @version 1.0
 * @date 19-4-17
 */
@Getter
@Setter
@ToString
public class ChapterExam extends AbstractExam {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String chapter;

    @JsonIgnore
    @Override
    public boolean isCompleteExamination() {
        return Tool.checkParamsNotNull(name,chapter) && choiceList != null && judgementList != null;
    }
}
