package com.bbd10.activemqredis.service.impl;

import com.bbd10.activemqredis.Dao.GoodsDao;
import com.bbd10.activemqredis.entity.Goods;
import com.bbd10.activemqredis.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.service
 * @date 2020/3/12 17:23 星期四
 */
@Service
public class GoodsServiceimpl  implements GoodsService{
    @Autowired
    private GoodsDao  goodsDao;

    @Override
    public List<Goods> queryAllGoods() {
        return goodsDao.queryAllGoods();
    }

    @Override
    public boolean addOrder(int uid, int gid) {
        if(goodsDao.addOrder(uid,gid)>0){
            return   true;
        }
        return false;
    }

    @Override
    public boolean updateStock(int gid) {
        if(goodsDao.updateStock(gid)>0){
            return   true;
        }
        return false;
    }

    @Override
    public Goods getgoofInfo(int gid) {
        return goodsDao.getgoofInfo(gid);
    }
}
