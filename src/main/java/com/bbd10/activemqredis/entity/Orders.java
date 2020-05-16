package com.bbd10.activemqredis.entity;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.entity
 * @date 2020/3/12 17:19 星期四
 */
//订单表
public class Orders {
    private   int oid;//订单编号
    private int  uid;//用户的主键 id
    private  int   gid;//商品的主键 id
    private  String  createTime;//创建时间

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
