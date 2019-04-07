package edu.qit.cloudclass.dao;

import edu.qit.cloudclass.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @author nic
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    /**
     * 董悦
     */
    void register(@Param("user") User user);

    /**
     * 董悦
     */
    User login(@Param("name") String name);

    /**
     * 董悦
     */
    void registerAutoLogin(@Param("id") String id,@Param("taken") String taken);

    /**
     * 董悦
     */
    User autoLogin(@Param("taken") String taken);

    /**
     * 董悦
     */
    int checkName(@Param("name") String username);

    /**
     * 董悦
     */
    int checkEmail(@Param("email") String email);
}
