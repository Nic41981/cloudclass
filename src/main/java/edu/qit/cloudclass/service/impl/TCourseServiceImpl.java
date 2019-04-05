package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.controller.UserController;
import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.PermissionMapper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.domain.User;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.service.PermissionService;
import edu.qit.cloudclass.tool.ResponseCode;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseServiceImpl implements CourseService {

    private final CourseMapper tcourseMapper;

    @Override
    public ServerResponse add(Course course) {
        if (course == null) {
            return ServerResponse.createByError(-1, "没有添加课程");
        }
        tcourseMapper.add(course);
        log.info("课程" + course.getId() + "添加成功!");
        return ServerResponse.createBySuccessMsg("课程添加成功");
    }

    @Override
    public ServerResponse modify(Course ttCourse) {
        /**
         * 修改时判断课程是否存在
         */
        String string = tcourseMapper.findTeacherIdByPrimaryKey(ttCourse.getId());
        if ( string == null) {
            log.info("课程已经不存在，请检查是否输入错误" );
            return ServerResponse.createByError("课程已经不存在，请检查是否输入错误");
        }

       if (tcourseMapper.modify(ttCourse)==1){
            log.info("更新课程成功 " + ttCourse.getName());

            return ServerResponse.createBySuccess("更新课程信息成功", ttCourse);
        }else{
            return ServerResponse.createByError(ResponseCode.ERROR.getStatus(),"修改失败");
        }
    }


    @Override
    public ServerResponse deleteCourseById(String id) {
        String string = tcourseMapper.findTeacherIdByPrimaryKey(id);
        if ( string == null) {
            log.info("课程已经删除，请尝试更新" );
            return ServerResponse.createByError("课程已删除,请尝试更新");
        }
        Course course = tcourseMapper.findCourseById(id);
        tcourseMapper.deleteCourseById(id);
        log.info("课程删除成功 " + course.getName() );
        return  ServerResponse.createBySuccessMsg("课程删除成功");
    }

    @Override
    public ServerResponse findCourseById(String id) {
       Course course = tcourseMapper.findCourseById(id);
       String string = tcourseMapper.findTeacherIdByPrimaryKey(id);
        if ( string == null) {
            log.info("找不到当前课程信息" + course.getName() );
            return ServerResponse.createByError("找不到当前课程信息");
        }
        log.info("Successful" + course.getName() );
        return ServerResponse.createBySuccess(course);
    }

    @Override
    public ServerResponse<List<Course>> getCourseList() {
        List<Course> list = tcourseMapper.getCourseList();
        if(!list.isEmpty()){
            log.info("当前课程查询成功");
            return ServerResponse.createBySuccess(list);
        }
        log.info("查询内容为空!");
        return ServerResponse.createBySuccessMsg("查询内容为空");
    }

    @Override
    public ServerResponse<List<Course>> getCourses(int pageNo, int pageSize, String teacher) {

        List<Course> list =  tcourseMapper.getCourses(pageNo,pageSize,teacher);
        if(!list.isEmpty()){
            log.info("当前老师的课程查询成功");
            return ServerResponse.createBySuccess(list);
        }
        log.info("当前老师没有课程" );
        log.info("查询内容为空!");
        return ServerResponse.createBySuccessMsg("当前老师没有课程");
    }
}
