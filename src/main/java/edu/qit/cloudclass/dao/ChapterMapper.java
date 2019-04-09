package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.spinner.ChapterSpinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterMapper {

    int insert(@Param("chapter") Chapter chapter);

    int delete(@Param("id") String id);

    int modify(@Param("chapter") Chapter chapter);

    List<Chapter> chapterList(@Param("course") String course);

    Chapter findChapterByPrimaryKey(@Param("id") String id);

    int findMaxNum(@Param("course") String course);

    int updateNumAfterDelete(@Param("num") int num,@Param("course")String course);

    int updateNumBeforeInsert(@Param("num") int num, @Param("course")String course);

    /**
     * 董悦
     */
    String findCourseIdByPrimaryKey(@Param("id") String id);

    /**
     * 董悦
     */
    int updateVideoIdAfterUpload(@Param("id") String id,@Param("video") String video);

    /**
     * 王恺
     */
    List<ChapterSpinner> getChapterSpinnerList(@Param("course") String course);

    /**
     * 李九龙
     */
    List<Chapter> CourseChapterList(@Param("course") String course);
}