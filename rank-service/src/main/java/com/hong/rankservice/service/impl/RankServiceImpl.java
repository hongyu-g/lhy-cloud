package com.hong.rankservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hong.common.redis.DataInfo;
import com.hong.common.redis.RedisUtil;
import com.hong.rankservice.bean.RankInfo;
import com.hong.rankservice.bean.UserCheckStatistic;
import com.hong.rankservice.dao.UserCheckStatisticDAO;
import com.hong.rankservice.service.RankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author liang
 * @description
 * @date 2020/7/22 15:04
 */
@Slf4j
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RedisUtil redisUtil;

    private static final String GLOBAL_RANK = "global_rank";


    private static final String key = "key_rank";

//    @Autowired
//    private MessageRecordDAO messageRecordDAO;

    @Autowired
    private UserCheckStatisticDAO userCheckStatisticDAO;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RankInfo rankInfo) {
        log.info("业务处理开始:{}", JSONObject.toJSONString(rankInfo));
        //接口幂等，去重日志表
//        try {
//            MessageRecord record = new MessageRecord();
//            record.setMessageKey(rankInfo.getKey());
//            messageRecordDAO.insert(record);
//        } catch (Exception e) {
//            log.info("消息重复消费，不做处理");
//            return;
//        }
//        long num = redisUtil.incr(key, 1);
//        if (num > 1) {
//            log.info("消息重复消费，不做处理");
//            return;
//        }

        int beforePassNum = 0;
        UserCheckStatistic statistic = userCheckStatisticDAO.query(rankInfo.getUserId());
        if (Objects.isNull(statistic)) {
            statistic = new UserCheckStatistic().setUserId(rankInfo.getUserId())
                    .setChallengeMysteryNum(0).setChallengeSuccessLordNum(0).setQuantity(1);
            userCheckStatisticDAO.insert(statistic);
        } else {
            beforePassNum = statistic.getQuantity();
            userCheckStatisticDAO.update(rankInfo.getUserId(), statistic.getQuantity());
        }
        String userId = rankInfo.getUserId() + "";
        ZSetOperations<String, Object> zSetOperations = redisUtil.getRedisTemplate().opsForZSet();

        Long count = zSetOperations.zCard(GLOBAL_RANK);
        if (count != null && count < 5) {
            zSetOperations.incrementScore(GLOBAL_RANK, userId, 1);
        } else {
            //最小值 判断用户数值
            DataInfo data = redisUtil.zGetScoreByRank(GLOBAL_RANK, 0, 1);
            if (data.getScore() >= beforePassNum + 1) {
                return;
            }
            Double score = redisUtil.zScore(GLOBAL_RANK, userId);
            if (Objects.nonNull(score)) {
                zSetOperations.incrementScore(GLOBAL_RANK, userId, 1);
                return;
            }
            zSetOperations.remove(GLOBAL_RANK, data.getValue());
            zSetOperations.incrementScore(GLOBAL_RANK, userId, beforePassNum + 1);

        }
    }
}