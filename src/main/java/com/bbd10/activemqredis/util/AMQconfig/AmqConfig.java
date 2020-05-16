package com.bbd10.activemqredis.util.AMQconfig;


import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;


/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.util.redis.AMQconfig
 * @date 2020/3/12 22:45 星期四
 */
@Configuration
@EnableJms
public class AmqConfig {

    //点对点
    @Bean
    public Queue queue(){
        return   new ActiveMQQueue("WQTest");
    }

    //发布订阅
    @Bean
    public Topic  topic(){
        return   new ActiveMQTopic("subWQ");
    }

    //用于连接工厂类型
    @Bean
    public JmsListenerContainerFactory  jmsListenerContainerFactory(ConnectionFactory  connectionFactory){
        DefaultJmsListenerContainerFactory   factory=new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    return  factory;
    }
}
