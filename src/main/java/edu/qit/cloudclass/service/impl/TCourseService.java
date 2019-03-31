package edu.qit.cloudclass.service.impl;

import edu.qit.cloudclass.dao.TCourseMapper;
import edu.qit.cloudclass.dao.UserMapper;
import edu.qit.cloudclass.domain.TCourse;
import edu.qit.cloudclass.tool.ServerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TCourseService implements edu.qit.cloudclass.service.TCourseService {

    private final TCourseMapper tCourseMapper;
    @Override
    public ServerResponse add(TCourse tCourse) {
        if (tCourse == null) {
            return ServerResponse.createByError(-1, "没有添加课程");
        }
        tCourseMapper.add(tCourse);
        log.info("课程" + tCourse.getId() + "添加成功!");
        return ServerResponse.createBySuccessMsg("课程添加成功");
    }

    @Override
    public ServerResponse modify(TCourse tCourse) {
        /**
         * 修改时判断课程是否存在
         */
//        tCourse = tCourseMapper.findCourseById(isid);
//        if ( tCourse  == null) {
//            return ServerResponse.createByError("课程不存在,请尝试更新");
//        }
        TCourse ttCourse = new TCourse();
        ttCourse.setId(tCourse.getId());
        ttCourse.setName(tCourse.getName());
        ttCourse.setImage(tCourse.getImage());
        ttCourse.setTag(tCourse.getTag());

        int updateCount = tCourseMapper.modify(ttCourse);
        if (updateCount > 0) {
            return ServerResponse.createBySuccess("更新课程信息成功", ttCourse);
        }
        return ServerResponse.createByError("更新课程信息失败");
    }

    @Override
    public ServerResponse deleteCourseById(String id) {
        TCourse tCourse = tCourseMapper.findCourseById(id);
        if ( tCourse  == null) {
            return ServerResponse.createByError("课程已删除,请尝试更新");
        }
        tCourseMapper.deleteCourseById(id);
        return  ServerResponse.createBySuccessMsg("课程删除成功");
    }

    @Override
    public ServerResponse<TCourse> findCourseById(String id) {
       TCourse tCourse = tCourseMapper.findCourseById(id);
        if (tCourse == null) {
            return ServerResponse.createByError("找不到当前课程信息");
        }
        return ServerResponse.createBySuccess(tCourse);
    }

    @Override
    public ServerResponse<List<TCourse>> getCourses(int pageNo, int pageSize) {
        return null;
    }
}
