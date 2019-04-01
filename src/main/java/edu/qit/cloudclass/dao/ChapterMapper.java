package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chapter
     *
     * @mbg.generated Sun Mar 31 10:35:47 CST 2019
     */
    int insert(Chapter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table chapter
     *
     * @mbg.generated Sun Mar 31 10:35:47 CST 2019
     */
    int insertSelective(Chapter record);

    List<Chapter> chapterList(@Param("course") String course);

    int searchNum(@Param("course") String course);

    int chapterModify(Chapter chapter);

    int selectChapter(@Param("id") String id);

    int deleteChapter(@Param("id") String id);

    /**
     * 董悦
     */
    String findCourseIdByPrimaryKey(@Param("id") String id);

}