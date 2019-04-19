package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.*;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionServiceImpl implements PermissionService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final StudyMapper studyMapper;
    private final FinalExamMapper finalExamMapper;
    private final ChapterExamMapper chapterExamMapper;

    @Override
    public ServerResponse checkCourseOwnerPermission(String userId, String courseId) {
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        if (course == null) {
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), "课程不存在");
        }

        if (course.getTeacher().equals(userId)) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(), "权限不足");
    }

    @Override
    public boolean isCourseExist(String courseId) {
        return courseMapper.checkCourseExist(courseId) == 1;
    }

    @Override
    public boolean isTeacherOfCourse(String userId, String courseId) {
        return courseMapper.findCourseByPrimaryKey(courseId).getTeacher().equals(userId);
    }

    @Override
    public String getCourseIdByChapterId(String chapterId) {
        return chapterMapper.findCourseIdByPrimaryKey(chapterId);
    }

    @Override
    public boolean isFinalExamExist(String examId) {
        return finalExamMapper.checkExamExist(examId) == 1;
    }

    @Override
    public String getCourseIdByFinalExam(String examId) {
        return courseMapper.fingCourseIdByFinalExam(examId);
    }

    @Override
    public boolean isMemberOfCourse(String userId, String courseId) {
        if (courseMapper.findTeacherIdByPrimaryKey(courseId).equals(userId)){
            return true;
        }
        return studyMapper.findStudyByCourseAndStudent(courseId, userId) != null;
    }

    @Override
    public boolean isChapterExamExist(String examId) {
        return chapterExamMapper.checkExamExist(examId) == 1;
    }

    @Override
    public String getCourseIdByChapterExam(String examId) {
        return chapterMapper.findCourseIdByChapterExam(examId);
    }
}
