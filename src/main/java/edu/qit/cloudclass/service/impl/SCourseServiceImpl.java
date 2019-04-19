package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.Study;
import edu.qit.cloudclass.domain.spinner.SCourseSpinner;
import edu.qit.cloudclass.service.SCourseService;
import edu.qit.cloudclass.tool.ResponseCode;
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

    @Override
    public ServerResponse scourseList(String studentId) {

        log.info(studentId);

        List<SCourseSpinner> listResult = courseMapper.getSCourseList(studentId);
        return ServerResponse.createBySuccess("查询成功", listResult);
    }

    @Override
    public ServerResponse insertScourse(String courseId, String studentId) {
        Study study = new Study();

        study.setCourse(courseId);
        study.setStudent(studentId);

        if (courseMapper.checkStudyByCourseIdAndStudentId(study) == 1) {
            ServerResponse.createByError(ResponseCode.ERROR.getStatus(), "已经选择该课程");
        }

        if (courseMapper.insertScourseByCourseIdAndStudentId(study) == 0) {
            ServerResponse.createByError(ResponseCode.ERROR.getStatus(), "选课异常");
        }

        return ServerResponse.createBySuccess("选课成功");
    }

    @Override
    public ServerResponse deleteScourse(String courseId, String studentId) {
        Study study = new Study();

        study.setCourse(courseId);
        study.setStudent(studentId);

        if (courseMapper.checkStudyByCourseIdAndStudentId(study) == 0) {
            ServerResponse.createByError(ResponseCode.ERROR.getStatus(), "未选修该课程");
        }

        if (courseMapper.deletScourseByCourseIdAndStudentId(study) == 0) {
            ServerResponse.createByError(ResponseCode.ERROR.getStatus(), "退课异常");
        }

        return ServerResponse.createBySuccess("退课成功");
    }

}
