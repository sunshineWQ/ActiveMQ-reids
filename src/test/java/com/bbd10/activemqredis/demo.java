package com.bbd10.activemqredis;

import com.bbd10.activemqredis.util.redis.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis
 * @date 2020/3/12 19:40 星期四
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class demo {
    @Autowired
    private RedisUtils  redisUtils;
    @Test
    public   void   demo1() throws Exception {
        redisUtils.setnx("c","123");
    }
}
