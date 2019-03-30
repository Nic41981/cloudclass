package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface FileMapper {
    void insert(@Param("file") FileInfo fileInfo);
}
