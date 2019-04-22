package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author nic
 * @version 1.0
 * @date 19-4-22
 */
@Mapper
public interface NoticeMapper {
    int insert(@Param("notice") Notice notice);

    List<Notice> selectNoticeListByCourse(@Param("course") String course);
}
