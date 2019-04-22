package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.StudyMapper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.domain.spinner.CourseSpinner;
import edu.qit.cloudclass.service.SCourseService;
import edu.qit.cloudclass.service.ScoreService;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SCourseServiceImpl implements SCourseService {
    private final CourseMapper courseMapper;
    private final StudyMapper studyMapper;
    private final ScoreService scoreService;

    @Override
    public ServerResponse courseList(String studentId) {
        List<Course> courseList = courseMapper.selectCourseListByStudent(studentId);
        return ServerResponse.createBySuccess("查询成功", courseList);
    }


    @Override
    public ServerResponse courseSpinnerList(String userId) {
        List<CourseSpinner> courseSpinnerList = courseMapper.selectCourseSpinnerListByStudent(userId);
        return ServerResponse.createBySuccess("查询成功", courseSpinnerList);
    }

    @Override
    public ServerResponse joinCourse(String courseId, String studentId) {
        if (studyMapper.checkStudyExistByCourseAndStudent(courseId,studentId) == 1){
            return ServerResponse.createByError("已经参加该课程");
        }
        Study study = new Study();
        study.setCourse(courseId);
        study.setStudent(studentId);
        log.info("创建学习:" + study.toString());
        if (studyMapper.insert(study) == 0){
            return ServerResponse.createByError("选课失败");
        }
        return ServerResponse.createBySuccessMsg("选课成功");
    }

    @Override
    public ServerResponse exitCourse(String courseId, String studentId) {
        if (studyMapper.checkStudyExistByCourseAndStudent(courseId,studentId) == 0){
            return ServerResponse.createByError("未参加该课程");
        }
        Study study = studyMapper.findStudyByCourseAndStudent(courseId,studentId);
        if (studyMapper.delete(study.getId()) == 0){
            return ServerResponse.createByError("退课失败");
        }
        scoreService.associateDelete(study.getId());
        return ServerResponse.createBySuccessMsg("退课成功");
    }

}
