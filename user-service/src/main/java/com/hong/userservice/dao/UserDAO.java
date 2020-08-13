package com.hong.userservice.dao;

import com.hong.userservice.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liang
 * @description
 * @date 2020/6/22 13:23
 */
@Repository
public interface UserDAO {

    @Select("select id,name from user where id = #{id} ")
    User getUser(@Param("id") Long id);


    @Update("update user set name=#{name} where id=#{id} ")
    void updateUser(@Param("id") Long id, @Param("name") String name);


    @Select("select id,name from user ")
    List<User> queryList(RowBounds rowBounds);

    @Select("select id,name from user ")
    List<User> queryList2();

    @Insert("insert into `user` (`name`) values (#{user.name}); ")
    int insert(@Param("user")User user);
}
