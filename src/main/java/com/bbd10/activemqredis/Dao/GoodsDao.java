package com.bbd10.activemqredis.Dao;

import com.bbd10.activemqredis.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.Dao
 * @date 2020/3/12 17:21 星期四
 */
@Component
public interface GoodsDao {
    //查询所有的商品
    List<Goods>queryAllGoods();
    //添加订单
    int  addOrder(@Param("uid") int uid,@Param("gid") int gid);
    //修改商品库存
    int  updateStock(int gid);
    //查询单个商品的信息
    Goods  getgoofInfo(int  gid);

}
