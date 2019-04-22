package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.spinner.ChapterSpinner;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.domain.spinner.ExamSpinner;
import edu.qit.cloudclass.service.SpinnerService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Root
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class SpinnerServiceImpl implements SpinnerService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final ChapterExamMapper chapterExamMapper;
    private final FinalExamMapper finalExamMapper;

    @Override
    public ServerResponse<List<CourseSpinner>> getCourseList() {
        List<CourseSpinner> courseList = courseMapper.selectCourseSpinnerList();
        return ServerResponse.createBySuccess("查询成功", courseList);
    }

    @Override
    public ServerResponse getChapterList(String courseId) {
        List<ChapterSpinner> courseList = chapterMapper.getChapterSpinnerList(courseId);
        return ServerResponse.createBySuccess("查询成功", courseList);
    }

    @Override
    public ServerResponse getExaminationList(String courseId) {
        List<ExamSpinner> chapterExamList = chapterExamMapper.selectExamSpinnerListByCourse(courseId);
        ExamSpinner finalExam = finalExamMapper.findExamSpinnerByCourse(courseId);
        Map<String,Object> result = new LinkedHashMap<>();
        result.put("chapterExamList",chapterExamList);
        result.put("finalExam",finalExam);
        return ServerResponse.createBySuccess("查询成功", result);
    }
}
