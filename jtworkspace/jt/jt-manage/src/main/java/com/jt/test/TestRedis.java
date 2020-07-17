package com.jt.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

public class TestRedis {
    /**
     * 1. spring整合Redis
     * 报错说明
     * 1).如果测试过程中报错则检查redis配置文件改3处
     * 2).检查redis启动方式redis- server redis . conf
     * 3).检查Linux的防火墙
     * 做完测试其他命令
     */
    @Test
    public void testString01() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        //2.操作Redis
        jedis.set("a", "redis入门案例");
        String value = jedis.get("a");
        System.out.println(value);
    }

    @Test
    public void testString02() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        //2.当前的数据是否存在
        if (jedis.exists("a")) {
            System.out.println(jedis.get("a"));
        } else {
            jedis.set("a", "测试是否存在的方法");
        }

    }
    /**
     * 1能都简化是否存在的判断
     * 2 如果该数据不存在时修改数据,否则不修改
     */
    @Test
    public void testString03() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存
        jedis.setnx("a","测试setnx方法1");
        jedis.setnx("a","测试setnx方法2");
        System.out.println(jedis.get("a"));
    }

    /**
     * setex方法 保证赋值操作和添加超时时间操作的原子性
     * 原子性:要么同时成功,要么同时不执行
     * @throws InterruptedException
     */
    @Test
    public void testString04() throws InterruptedException {

        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存

        jedis.set("a","aaa");//程序报错,永不超时
        jedis.expire("a",20);//添加超时时间
        Thread.sleep(2000);
        System.out.println("剩余存活时间"+jedis.ttl("a"));

        //2实现原子性操作
        jedis.setex("b",20,"原子性测试");
        System.out.println(jedis.get("b"));
    }

    /**
     * 1 只有数据不存在时允许修改
     * 2 要求实现添加超时时间,并且是原子性操作
     * SetParams
     * 1.NX  只有key不存在,才能修改
     * 2.XX  只有key存在时，才能修改
     * 3.PX  添加的时间单位是毫秒
     * 4.EX  添加的时间单位是秒
     */
    @Test
    public void testString05() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存
        SetParams params = new SetParams();
        params.nx().ex(20);
        jedis.set("aa","测试A",params);
        jedis.set("aa","测试B",params);
        System.out.println(jedis.get("aa"));
    }

    @Test
    public void testString06() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存
      jedis.hset("user","name","tomcat");
      jedis.hset("user","id","100");
        System.out.println(jedis.hgetAll("user"));
    }

    @Test
    public void testString07() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存
        jedis.lpush("list","1","2","3","4","5");
        System.out.println(jedis.rpop("list"));
    }

    @Test
    public void testTx() {
        //1.创建jedis对象
        Jedis jedis = new Jedis("192.168.126.129", 6379);
        jedis.flushAll();//清空所有的redis缓存

        Transaction transaction = jedis.multi();
        try {
            transaction.set("aaa", "aaa");
            transaction.set("bbb", "bbbbb");
            transaction.set("ccc", "cccccc");
            transaction.exec();//事务提交
        }catch (Exception e){
            e.printStackTrace();;
            transaction.discard();//事务回滚

        }
    }

}