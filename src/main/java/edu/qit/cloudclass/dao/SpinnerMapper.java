package edu.qit.cloudclass.dao;

/**
 * Created by Root
 */
import edu.qit.cloudclass.domain.ChapterSpinner;
import edu.qit.cloudclass.domain.CourseSpinner;
import edu.qit.cloudclass.domain.ExamSpinner;
import edu.qit.cloudclass.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SpinnerMapper {

    List<CourseSpinner> getCourseList();

    List<ChapterSpinner> getChapterList(String courseId);

    List<ExamSpinner> getExaminationList(String courseId);

}