package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Chapter;
import edu.qit.cloudclass.domain.ChapterSpinner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterMapper {

    int insert(Chapter record);

    int insertSelective(Chapter record);

    List<Chapter> chapterList(@Param("courseId") String courseId);

    int findNextNum(@Param("courseId") String courseId);

    int chapterModify(@Param("chapter") Chapter chapter);

    int findNumByPrimaryKey(@Param("id") String id);

    int deleteChapter(@Param("chapterId") String chapterId);

    int updateNumAfterDelete(@Param("num") int num,@Param("course")String course);

    int updateNumBeforeInster(@Param("num") int num, @Param("course")String course);

    /**
     * 董悦
     */
    String findCourseIdByPrimaryKey(@Param("id") String id);

    /**
     * 董悦
     */
    int updateVideoIdAfterUpload(@Param("id") String id,@Param("video") String videoId);

    /**
     * 王恺
     */
    List<ChapterSpinner> getChapterSpinnerList(String courseId);
}