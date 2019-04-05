package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterExamMapper;
import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.ChapterSpinner;
import edu.qit.cloudclass.domain.CourseSpinner;
import edu.qit.cloudclass.domain.ExamSpinner;
import edu.qit.cloudclass.service.SpinnerService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    @Override
    public ServerResponse<List<CourseSpinner>> getCourseList() {
       List<CourseSpinner> courseList = courseMapper.getCourseSpinnerList();
       return ServerResponse.createBySuccess("查询成功",courseList);
    }

    @Override
    public ServerResponse getChapterList(String courseId) {
        ServerResponse existResult = checkCourseExist(courseId);
        if (!existResult.isSuccess()){
            return existResult;
        }
        List<ChapterSpinner> courseList = chapterMapper.getChapterSpinnerList(courseId);
        return ServerResponse.createBySuccess("查询成功",courseList);
    }

    @Override
    public ServerResponse getExaminationList(String courseId) {
        ServerResponse existResult = checkCourseExist(courseId);
        if (!existResult.isSuccess()){
            return existResult;
        }
        List<ExamSpinner> examinationList = chapterExamMapper.getExamSpinnerList(courseId);
        return ServerResponse.createBySuccess("查询成功",examinationList);
    }

    @Override
    public ServerResponse checkCourseExist(String courseId) {
        int flag = courseMapper.checkCourseExist(courseId);
        if (flag == 0){
            return ServerResponse.createByError(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),"课程不存在");
        }
        return ServerResponse.createBySuccess();
    }
}
