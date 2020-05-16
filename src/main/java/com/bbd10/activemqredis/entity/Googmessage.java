package com.bbd10.activemqredis.entity;

import java.io.Serializable;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.entity
 * @date 2020/3/12 23:28 星期四
 */
//MQ要发送的信息
public class Googmessage  implements Serializable {
    private   int uid;
    private  int gid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
