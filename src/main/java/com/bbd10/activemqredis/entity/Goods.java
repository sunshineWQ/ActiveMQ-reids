package com.bbd10.activemqredis.entity;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.entity
 * @date 2020/3/12 17:17 星期四
 */
//商品表
public class Goods {

    private   int  gid;//商品编号
    private   String  gname;//商品名称
    private  int  stock;//商品库存

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "gid=" + gid +
                ", gname='" + gname + '\'' +
                ", stock=" + stock +
                '}';
    }
}
