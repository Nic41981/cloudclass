package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.ExamMapper;
import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.Exam;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author nic
 * @version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionServiceImpl implements PermissionService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;
    private final ExamMapper examMapper;
    private final StudyMapper studyMapper;

    @Override
    public ServerResponse checkCourseOwnerPermission(String userId, String courseId) {
        String teacher = courseMapper.findTeacherIdByPrimaryKey(courseId);
        if (teacher == null){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }

        if (teacher.equals(userId)){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError(ResponseCode.PERMISSION_DENIED.getStatus(),"权限不足");
    }

    @Override
    public ServerResponse checkChapterOwnerPermission(String userId,String courseId, String chapterId) {
        String targetCourse = chapterMapper.findCourseIdByPrimaryKey(chapterId);
        if (targetCourse == null || !targetCourse.equals(courseId)){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"章节不存在");
        }
        return checkCourseOwnerPermission(userId,targetCourse);
    }
}
