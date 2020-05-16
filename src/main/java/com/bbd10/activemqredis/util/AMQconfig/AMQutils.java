package com.bbd10.activemqredis.util.AMQconfig;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.util.redis.AMQconfig
 * @date 2020/3/12 22:55 星期四
 */

@Component
public class AMQutils {
    @Resource
    private JmsTemplate  jmsTemplate;
    @Resource
    private Queue queue;
    @Resource
    private Topic  topic;

      public  void   sendQueueMsg(Object  info){
        jmsTemplate.convertAndSend(queue,info);
    }
    public  void   sendTopicMsg(Object  info){
        jmsTemplate.convertAndSend(topic,info);
    }


}
