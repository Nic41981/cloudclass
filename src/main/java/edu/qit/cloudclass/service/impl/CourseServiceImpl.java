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

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseServiceImpl implements CourseService {

    private final CourseMapper tCourseMapper;
    private final ChapterMapper chapterMapper;
    @Override
    public ServerResponse<List<Course>> TagList(String tag) {
        List<Course> list =  tCourseMapper.TagList(tag);
        return ServerResponse.createBySuccess("查询成功",list);
    }


}
