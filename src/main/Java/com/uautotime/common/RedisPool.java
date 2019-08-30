package com.uautotime.common;

import com.uautotime.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yaosheng on 2019/8/26.
 */
public class RedisPool {

    private static JedisPool pool;                 //Jedis的连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));             //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","20"));               //在JedisPool中最大的idel(空闲）状态的Jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","20"));               //在JedisPool中最小的idel(空闲）状态的Jedis实例的个数
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));         //在borrow一个Jedis实例的时候，是否需要进行验证操作，如果赋值为true，则得到的Jedis实例是可以使用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));         //在retuen一个Jedis实例的时候，是否需要进行验证操作，如果赋值为true，则放回JedisPool的Jedis实例是可以使用的

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static void initPolol(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true);                //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时，默认为true
        pool = new JedisPool(config,redisIp,redisPort,1000*2);

    }

    static{
        initPolol();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        if(jedis != null){
            pool.returnBrokenResource(jedis);
        }

    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("Jeson","yaosheng");
        pool.destroy();                             //临时调用，销毁连接池中的所有连接
        System.out.println("program is end");
    }

}



















