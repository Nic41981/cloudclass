package edu.qit.cloudclass.service;

import edu.qit.cloudclass.tool.ServerResponse;

/**
 * @author nic
 * @version 1.0
 */
public interface PermissionService {
    ServerResponse checkCourseOwnerPermission(String userId, String courseId);

    boolean isCourseExist(String courseId);

    boolean isTeacherOfCourse(String userId,String courseId);

    String getCourseIdByChapterId(String chapterId);

    boolean isFinalExamExist(String examId);

    String getCourseIdByFinalExam(String examId);

    boolean isMemberOfCourse(String userId,String courseId);

    boolean isChapterExamExist(String examId);

    String getCourseIdByChapterExam(String examId);
}
