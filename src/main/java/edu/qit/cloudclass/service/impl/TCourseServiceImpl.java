package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.NoticeMapper;
import edu.qit.cloudclass.dao.UserMapper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Notice;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.service.*;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseServiceImpl implements TCourseService {

    private final CourseMapper courseMapper;
    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;
    private final FileService fileService;
    private final TChapterService tChapterService;
    private final StudyService studyService;
    private final FinalExamService finalExamService;
    private final UploadService uploadService;

    @Override
    public ServerResponse<List<Course>> getCourses(String teacherId) {
        List<Course> list = courseMapper.selectCoursesListByTeacher(teacherId);
        String teacher = userMapper.getUserNameByPrimaryKey(teacherId);
        for (Course course:list){
            course.setTeacher(teacher);
        }
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @Override
    public ServerResponse courseSpinner(String teacherId) {
        List<CourseSpinner> courseSpinnerList = courseMapper.selectCourseSpinnerListByTeacher(teacherId);
        return ServerResponse.createBySuccess("查询成功",courseSpinnerList);
    }

    @Override
    public ServerResponse publishNotice(Notice notice) {
        log.info("创建公告:" + notice);
        if (noticeMapper.insert(notice) == 0){
            return ServerResponse.createByError("创建失败");
        }
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse add(Course course, MultipartFile image) {
        course.setId(Tool.uuid());
        log.info("创建课程:" + course.toString());
        if (courseMapper.insert(course) == 0) {
            log.error("创建失败");
            return ServerResponse.createByError("创建失败");
        }
        if (image != null) {
            uploadService.uploadCourseImage(image, course.getId());
        }
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse modify(Course course) {
        log.info("更新课程:" + course.toString());
        if (courseMapper.modify(course) == 0) {
            log.error("更新失败");
            return ServerResponse.createByError("更新失败");
        }
        return ServerResponse.createBySuccessMsg("更新成功");

    }

    @Override
    public ServerResponse deleteCourseById(String courseId) {
        log.info("==========删除课程开始==========");
        //查找记录
        Course course = courseMapper.findCourseByPrimaryKey(courseId);
        //删除记录
        log.info("删除课程:" + course.toString());
        if (courseMapper.delete(courseId) == 0) {
            log.error("==========课程记录删除失败==========");
            return ServerResponse.createByError("删除失败");
        }
        //级联删除
        if (course.getImage() != null) {
            fileService.associateDelete(course.getImage());
        }
        if (course.getFinalExam() != null){
            finalExamService.associateDelete(course.getFinalExam());
        }
        tChapterService.associateDelete(course.getId());
        studyService.associateDelete(course.getId());
        log.info("==========删除课程结束==========");
        return ServerResponse.createBySuccessMsg("删除成功");
    }

}
