package com.hong.rankservice.dao;

import com.hong.rankservice.bean.UserCheckStatistic;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author liang
 * @description
 * @date 2020/7/23 17:23
 */
@Repository
public interface UserCheckStatisticDAO {

    @Insert("insert into user_check_statistic(user_id,quantity,challengeSuccessLordNum,challengeMysteryNum) " +
            " select #{record.userId},#{record.quantity},#{record.challengeSuccessLordNum},#{record.challengeMysteryNum} " +
            " ")
    void insert(@Param("record") UserCheckStatistic record);


    @Select("select * from user_check_statistic where user_id = #{userId}")
    UserCheckStatistic query(@Param("userId") Long userId);

    @Update("update user_check_statistic set quantity=quantity+1 where user_id = #{userId} and quantity=#{quantity}")
    void update(@Param("userId") Long userId, @Param("quantity") Integer quantity);
}
