package com.hong.userservice.dao;

import com.hong.userservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author liang
 * @description
 * @date 2020/7/10 18:24
 */
@Repository
public interface UserJpaDAO extends JpaRepository<User, Long> {

    /**
     * 手写sql 占位符传值形式
     */
    @Query(value = "update user set name=? where id=?", nativeQuery = true)
    @Modifying
    int updateById(String name, Long id);
}
