package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.CourseMapper;
import edu.qit.cloudclass.dao.PermissionMapper;
import edu.qit.cloudclass.domain.Course;
import edu.qit.cloudclass.service.CourseService;
import edu.qit.cloudclass.service.PermissionService;
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

    private final CourseMapper courseMapper;

    private final PermissionMapper permissionMapper;
    private final PermissionService permissionService;
    @Override
    public ServerResponse add(Course course) {
        if (course == null) {
            return ServerResponse.createByError(-1, "没有添加课程");
        }
        courseMapper.add(course);
        log.info("课程" + course.getId() + "添加成功!");
        return ServerResponse.createBySuccessMsg("课程添加成功");
    }

    @Override
    public ServerResponse modify(Course course) {
        /**
         * 修改时判断课程是否存在
         */
//        course = tCourseMapper.findCourseById(isid);
//        if ( course  == null) {
//            return ServerResponse.createByError("课程不存在,请尝试更新");
//        }
        Course ttCourse = new Course();
        ttCourse.setId(course.getId());
        ttCourse.setName(course.getName());
        ttCourse.setImage(course.getImage());
        ttCourse.setTeacher(course.getTeacher());
        ttCourse.setTag(course.getTag());
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(ttCourse.getTeacher(), ttCourse.getId());
        if (!permissionResult.isSuccess()){
            int updateCount = courseMapper.modify(ttCourse);
            if (updateCount > 0) {
                log.info("更新课程成功" + course.getName());
                return ServerResponse.createBySuccess("更新课程信息成功", ttCourse);
            }
        }else{
        //if(permissionMapper.checkCourseOwnerPermission(course.getTeacher(), course.getId())){
//            return permissionResult;
            return ServerResponse.createByError("权限不足");
        }
        log.info("更新课程失败" + course.getName() );
        return ServerResponse.createByError("权限不足");
    }
    @Override
    public ServerResponse deleteCourseById(String id) {
        Course course = courseMapper.findCourseById(id);
        if ( course == null) {
            log.info("课程已经删除，请尝试更新" + course.getName() );
            return ServerResponse.createByError("课程已删除,请尝试更新");
        }
        //if(permissionMapper.checkCourseOwnerPermission(course.getTeacher(), course.getId())){
        ServerResponse permissionResult = permissionService.checkCourseOwnerPermission(course.getTeacher(), course.getId());
        if (!permissionResult.isSuccess()){
//            return permissionResult;
            courseMapper.deleteCourseById(id);
            log.info("课程删除成功 " + course.getName() );
            return  ServerResponse.createBySuccessMsg("课程删除成功");
        }else{
            return ServerResponse.createByError("权限不足");
        }
//        return  ServerResponse.createByError("权限不足");
    }

    @Override
    public ServerResponse<Course> findCourseById(String id) {
       Course course = courseMapper.findCourseById(id);
        if (course == null) {
            log.info("找不到当前课程信息" + course.getName() );
            return ServerResponse.createByError("找不到当前课程信息");
        }
        log.info("Successful" + course.getName() );
        return ServerResponse.createBySuccess(course);
    }
    @Override
    public ServerResponse<List<Course>> getCourses(int pageNo, int pageSize, String teacher) {

        List<Course> list =  courseMapper.getCourses(pageNo,pageSize,teacher);
        if(!list.isEmpty()){
            log.info("当前老师的课程查询成功");
            return ServerResponse.createBySuccess(list);
        }
        log.info("当前老师没有课程" );
        return ServerResponse.createByError("当前老师的无课程");
    }
}
