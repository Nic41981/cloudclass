package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.NoticeMapper;
import edu.qit.cloudclass.dao.UserMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.Notice;
import edu.qit.cloudclass.service.CourseService;
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
    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;

    @Override
    public ServerResponse<List<Course>> tagList(String tag) {
        List<Course> list = courseMapper.selectCouserListByTag(tag);
        for (Course course : list){
            course.setTeacher(userMapper.getUserNameByPrimaryKey(course.getTeacher()));
        }
        return ServerResponse.createBySuccess("查询成功", list);
    }

    @Override
    public ServerResponse courseChapterList(String courseId) {
        List<Chapter> list = chapterMapper.chapterList(courseId);
        String finalExam = courseMapper.findFinalExamByPrimaryKey(courseId);
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("chapterList", list);
        resultMap.put("finalExam", finalExam);
        return ServerResponse.createBySuccess("查询成功", resultMap);
    }

    @Override
    public ServerResponse getNotices(String courseId) {
        List<Notice> noticeList = noticeMapper.selectNoticeListByCourse(courseId);
        return ServerResponse.createBySuccess("查询成功",noticeList);
    }

}
