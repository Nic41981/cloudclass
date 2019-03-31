package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    void register(@Param("user") User user);

    User login(@Param("name") String name);

    void registerAutoLogin(@Param("id") String id,@Param("taken") String taken);

    User autoLogin(@Param("taken") String taken);

    int checkName(@Param("name") String username);

    int checkEmail(@Param("email") String email);
}
