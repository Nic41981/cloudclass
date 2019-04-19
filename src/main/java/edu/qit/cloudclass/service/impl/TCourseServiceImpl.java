package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.service.*;
import edu.qit.cloudclass.tool.ServerResponse;
import edu.qit.cloudclass.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseServiceImpl implements TCourseService {

    private final CourseMapper tCourseMapper;
    private final FileService fileService;
    private final TChapterService tChapterService;
    private final StudyService studyService;
    private final FinalExamService finalExamService;

    @Override
    public ServerResponse<List<Course>> getCourses(String teacher) {
        List<Course> list = tCourseMapper.coursesList(teacher);
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @Override
    public ServerResponse add(Course course) {
        course.setId(Tool.uuid());
        log.info("创建课程:" + course.toString());
        if (tCourseMapper.insert(course) == 0) {
            log.error("创建失败");
            return ServerResponse.createByError("创建失败");
        }
        return ServerResponse.createBySuccessMsg("创建成功");
    }

    @Override
    public ServerResponse modify(Course course) {
        log.info("更新课程:" + course.toString());
        if (tCourseMapper.modify(course) == 0) {
            log.error("更新失败");
            return ServerResponse.createByError("更新失败");
        }
        return ServerResponse.createBySuccessMsg("更新成功");

    }

    @Override
    public ServerResponse deleteCourseById(String courseId) {
        log.info("==========删除课程开始==========");
        //查找记录
        Course course = tCourseMapper.findCourseByPrimaryKey(courseId);
        //删除记录
        log.info("删除课程:" + course.toString());
        if (tCourseMapper.delete(courseId) == 0) {
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
