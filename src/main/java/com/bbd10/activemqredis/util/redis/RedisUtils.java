package com.bbd10.activemqredis.util.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 写redis的增删改查
 *
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public boolean set(String key,String value){
        try{
            //只能存储String类型的key
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            //只能存储String类型的value
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            //新增一个字符串类型的值，key是键，value是值
            //ValueOpertions 说明：只能一个键对应一个值.可以存放Map List等json.
            ValueOperations<String,Object> vo=redisTemplate.opsForValue();
            vo.set(key,value);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key,String value,long seconds){
        try{
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());

            ValueOperations<String,Object> vo=redisTemplate.opsForValue();
            vo.set(key,value);
            expire(key,seconds);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean expire(final String key,final long expireTime){
        return redisTemplate.execute(new RedisCallback<Boolean>(){
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                boolean flag=false;
                try{
                    redisTemplate.setKeySerializer(new StringRedisSerializer());
                    redisTemplate.setValueSerializer(new StringRedisSerializer());

                    byte[] keys=new StringRedisSerializer().serialize(key);

                    flag=redisConnection.expire(keys,expireTime);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return flag;
            }
        });
    }


    public   Long  getExpire(String   key){

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String,Object> vo=redisTemplate.opsForValue();

        return redisTemplate.getExpire(key);
    }



    public   Object  get(String  key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String,Object> vo=redisTemplate.opsForValue();
        return vo.get(key);
    }

    public   void  delete(String key){
        try{
             redisTemplate.setKeySerializer(new StringRedisSerializer());
             redisTemplate.delete(key);
        }catch (Exception  e){
            e.printStackTrace();
        }
    }

    public   boolean   exists(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations<String,Object> vo=redisTemplate.opsForValue();
        Object  value=vo.get(key);
        if(value==null ||  value==""){
           return   false;
        }else {
            return   true;
        }
    }

    public   boolean  update(String  key,String value){


        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String,Object> vo=redisTemplate.opsForValue();
        Long  expireTime=redisTemplate.getExpire(key);
        if(expireTime==null){
            return   false;
        }else   if(expireTime==0 ||expireTime==-2){
            return   false;
        }
        vo.set(key,value);
        if(expireTime>0){
            expire(key,expireTime);
        }
        return   true;

    }

    public   boolean  setnx(final   String  key,final   String value)throws Exception{
        return  redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
            boolean  flag=false;
            try {
                redisTemplate.setKeySerializer(new StringRedisSerializer());
                redisTemplate.setValueSerializer(new StringRedisSerializer());
                StringRedisSerializer  stringRedisSerializer=new StringRedisSerializer();
                byte   keys[]=stringRedisSerializer.serialize(key);
                byte  values[]=stringRedisSerializer.serialize(value);
                flag=redisConnection.setNX(keys,values);
            }catch (Exception  e){
                e.printStackTrace();
            }finally {
                return   flag;
            }

            }
        });
    }


}

