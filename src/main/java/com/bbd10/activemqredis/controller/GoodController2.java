package com.bbd10.activemqredis.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bbd10.activemqredis.entity.Goods;
import com.bbd10.activemqredis.entity.Googmessage;
import com.bbd10.activemqredis.service.GoodsService;
import com.bbd10.activemqredis.util.AMQconfig.AMQutils;
import com.bbd10.activemqredis.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 王青
 * @version V1.0
 * @Project: activemq-redis
 * @Package com.bbd10.activemqredis.controller
 * @date 2020/3/12 17:29 星期四
 */
@Controller
public class GoodController2 {
    @Autowired
    private GoodsService  goodsService;
    @Autowired
    private RedisUtils  redisUtils;
    @Resource
    private AMQutils  amQutils;
    //因为redis  中键的值都是String 类型的   所以这块声明一个字符串下面设置key 的时候u用来拼接
    //key:"goodsprefix:101"   value:"gname"  (商品的名称，商品的图片，商品的价格  打成JSON字符串以键值对的方式存入在redis中)
    private final   String  goodsprefix="goodInfo:";
    private final   String  goodstock="goodstock:";

    private final   String  lock="lock:";//别人在操作得时候 我就不操作     //key :lock:101,vaule=yes


    private final   String  getStatus="got";//key : got:123,    value=success/failed
    private final   String  getSuccess="success";
    private final   String  getFailed="failed";  //预防一个人别买多次
    @RequestMapping("/queryAllGoodsTest")
    public  String  queryAllGoods(Model  model){
        List<Goods>goodsList= goodsService.queryAllGoods();
        model.addAttribute("goodsList",goodsList);
        return "allgoos";
    }



    @RequestMapping("/getGoodsInfoTest")
    public  String  getGoodsInfo(int gid,Model  model) throws Exception {
        //秒杀高峰时期  查看商品详情要查询的数据比较频繁 防止mysql  崩溃 所以就用redis   redis   读取数据的速度是特别快  高并发峰值达到1秒中1000
        //先去redis中查询是否有该key
        Goods  goods;//数据查到的goods
        String gname;  //redis  中查到的
        String stock;//

        if(redisUtils.exists(goodsprefix+gid)){//到这块说明   redis  中有 这个key
            //有key 的话   就根据key  查询商品
            gname=(String) redisUtils.get(goodsprefix+gid);
            //获取库存
            stock=(String) redisUtils.get(goodstock+gid);
            if(stock.equals("0")){
                stock="已经没有了";
            }

            model.addAttribute("goods",JSONObject.parseObject(gname,Goods.class));
        }else {  //没有key
            goods=goodsService.getgoofInfo(gid);//到数据库中查询  然后放在redis 中
            redisUtils.setnx(goodsprefix+gid,JSON.toJSONString(goods));
            //设置库存
            redisUtils.setnx(goodstock+gid,goods.getStock()+"");
            model.addAttribute("goods",goods);
        }
        //model.addAttribute("goods",goods);
        return "goodsDetails";
    }



    @Transactional
    @RequestMapping("/buyTest")   //中间件   只负责接收Amq  数据
    @ResponseBody
    public   void   buy(int  uid,int gid,Model model){ // 点击购买要生成订单
        //生成订单之前先 查看库存
       /* Goods  goods=goodsService.getgoofInfo(gid);
        if(goods.getStock()>0){
             model.addAttribute("msg","正在抢购。。。");
             goodsService.addOrder(uid,gid);
             goodsService.updateStock(gid);
        }else {
            model.addAttribute("msg","抢光了");
        }
*/
        Googmessage  googmessage=new Googmessage();
        googmessage.setGid(gid);
        googmessage.setUid(uid);

        try {
            amQutils.sendQueueMsg(googmessage);
            System.out.println(uid+"订单已经在处理");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("MQ传输异常");
        }

    }

    //  监听  Amq  收到得数据 并做业务处理
    @JmsListener(destination = "WQTest")
    public   void   deal(Googmessage  googmessage)throws Exception{
        int uid=googmessage.getUid();
        int gid=googmessage.getGid();


//        while (!redisUtils.setnx(lock+gid,"yes")){  //别人正在操作  我们不能操作   如果没有Setnx 成功  代表有人在数据   就让线程先睡十毫秒  如果Setnx  成功 就走下一步
//            Thread.sleep(10);
//        }

        if(redisUtils.exists(getStatus+uid)){  //去数据库查询他得购买情况  是否之前购买过  查找数据库是否有该键
            //证明买过
            if(redisUtils.get(getStatus+uid).toString().equals(getSuccess)){
                //证明买过  并且成功了
                redisUtils.delete(lock+uid);
                System.out.println("已经买过了 ，不能再买");
                return;
            }else {
                //买过  但没成功
            }
        }else {
            //说明这个人没买过
        }

        // 这个人没买过  判断库存     根据商品的id 查询商品的库存
        String curr_stock_str=redisUtils.get(goodstock+gid).toString();
        int curr_stock=new  Integer(curr_stock_str);//转换为int 类型
        if(curr_stock<=0){
            System.out.println("库存不足");
            //redisUtils.delete(lock+gid);
            redisUtils.set(getStatus+uid,getFailed);
            return;
        }
        //到这说明  商品有库存  并且这个用户没买过
        curr_stock-=1;
        redisUtils.set(goodstock+gid,curr_stock+"");//设置redis  中商品得库存数量
       // redisUtils.delete(lock+gid); //删除锁  我不操作这个商品  让下一个用户来
        goodsService.updateStock(gid);//更新数据库商品得库存
        goodsService.addOrder(uid,gid);//添加订单表
        redisUtils.set(getStatus+uid,getSuccess);//这个用户购买成功
        System.out.println(uid+"抢购成功");



    }
}
