package com.bbd10.activemqredis.util.redis;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author 王青
 * @version V1.0
 * @Project: demo-login
 * @Package com.bd10.demologin.util
 * @date 2020/1/5 09:25 星期日
 *
 *  用于定义配置类  连接redis  她可替换xml配置文件被注解的类内部包含有一个或多个被@Bean
 *  注解的方法，这些方法会被AnnotationConfigApplicationContext 或AnnotationConfigWebApplicationContext 类 进行扫描，并用于构建bean定义，初始化spring容器
 */
@Configuration
@EnableCaching
    public class redisConfig extends CachingConfigurerSupport {
    /**
     * redisTemplate   相关配置
     * @param factory
     * @return
     */

    @Bean
    public   RedisTemplate<String,String>redisTemplate(RedisConnectionFactory   factory){
         RedisTemplate<String,String> redisTemplate=new RedisTemplate<>();
         //配置连接工厂
            redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
            return  redisTemplate;
        }
    }
