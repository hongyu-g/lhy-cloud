package com.hong.rankservice.dao;

import com.hong.rankservice.bean.MessageRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author liang
 * @description
 * @date 2020/7/23 17:23
 */
//@Repository
public interface MessageRecordDAO {

    @Insert("insert into message_record(message_key) values(#{record.messageKey})")
    void insert(@Param("record")MessageRecord record);
}
