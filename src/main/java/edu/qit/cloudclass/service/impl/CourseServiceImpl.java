package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.ChapterMapper;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.Course;
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
    @Override
    public ServerResponse<List<Course>> tagList(String tag) {
        List<Course> list =  courseMapper.tagList(tag);
        return ServerResponse.createBySuccess("查询成功",list);
    }

    @Override
    public ServerResponse courseChapterList(String course) {
        List<Chapter> list =  chapterMapper.courseChapterList(course);
        String finalExam = courseMapper.findFinalExamByPrimaryKey(course);
        Map<String,Object> resultMap = new LinkedHashMap<>();
        resultMap.put("chapterList",list);
        resultMap.put("finalExam",finalExam);
        return ServerResponse.createBySuccess("查询成功",resultMap);
    }

}
