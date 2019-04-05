package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.ExamMapper;
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
    private final ExamMapper examMapper;

    @Override
    public ServerResponse<List<CourseSpinner>> getCourseList() {
       List<CourseSpinner> courseList = courseMapper.getCourseList();
       if (courseList!=null){
           return ServerResponse.createBySuccess("查询课程列表成功",courseList);
       }

        return ServerResponse.createByError("查询课程列表失败");
    }

    @Override
    public ServerResponse<List<ChapterSpinner>> getChapterList(String courseId) {
        if (courseId==null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        List<ChapterSpinner> courseList = chapterMapper.getChapterList(courseId);
        if (courseList!=null&&courseList.size()!=0){
            return ServerResponse.createBySuccess("查询章节列表成功",courseList);
        }
        return ServerResponse.createByError("查询章节列表失败");
    }

    @Override
    public ServerResponse<List<ExamSpinner>> getExaminationList(String courseId) {
        List<ExamSpinner> examinationList = examMapper.getExaminationList(courseId);
        if (courseId==null){
            return ServerResponse.createByError(ResponseCode.MISSING_ARGUMENT.getStatus(),"缺少参数");
        }
        if (examinationList!=null&&examinationList.size()!=0){
            return ServerResponse.createBySuccess("查询考试名称列表成功",examinationList);
        }

        return ServerResponse.createByError("查询考试名称列表失败");
    }
}
