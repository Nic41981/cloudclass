package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final StudyMapper studyMapper;

    @Override
    public ServerResponse<List<Course>> tagList(String tag) {
        List<Course> list = courseMapper.tagList(tag);
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @Override
    public ServerResponse courseChapterList(String courseId, String userId) {
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "课程不存在");
        }
        if (!course.getTeacher().equals(userId)) {
            Study study = studyMapper.findStudyByCourseAndStudent(course.getId(), userId);
            if (study == null) {
                return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "未参加学习");
            }
        }
        List<Chapter> list = chapterMapper.courseChapterList(course.getId());
        String finalExam = courseMapper.findFinalExamByPrimaryKey(course.getId());
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("chapterList", list);
        resultMap.put("finalExam", finalExam);
        return ServerResponse.createBySuccess("查询成功", resultMap);
    }

}
