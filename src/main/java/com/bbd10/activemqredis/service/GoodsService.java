package com.bbd10.activemqredis.service;

import com.bbd10.activemqredis.entity.Goods;

import java.util.List;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.service
 * @date 2020/3/12 17:23 星期四
 */
public interface GoodsService {
    List<Goods> queryAllGoods();
    boolean  addOrder(int uid,int gid);
    boolean  updateStock(int gid);
    Goods  getgoofInfo(int  gid);
}
