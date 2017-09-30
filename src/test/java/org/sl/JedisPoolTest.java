package org.sl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {

    public static void main(String[] args) {
        /**
         * 01.JedisPoolConfig 读取和设置参数
         */
        JedisPoolConfig conf = new JedisPoolConfig();
        // 设置连接池的最大连接数量
        conf.setMaxTotal(10);
        // 设置最小活跃数量
        conf.setMinIdle(5);
        /**
         * 02. JedisPool 连接池
         */
        JedisPool pool = new JedisPool(conf, "192.168.1.4", 6379);
        /**
         * 03. Jedis 操作对象
         */
        Jedis jedis = pool.getResource(); // 从连接池中获取对象
        jedis.set("poolKey", "poolValue"); // 向redis增加数据
        String value = jedis.get("poolKey");// 从redis中获取数据
        System.out.println("poolKey对应的value=" + value);

        // 关闭资源
        pool.returnResourceObject(jedis);
    }
}