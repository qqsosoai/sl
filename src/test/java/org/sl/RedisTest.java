package org.sl;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {

        /**
         * 创建Jedis对象
         * 01.host 主机地址
         * 02.port 端口号
         */
        Jedis jedis = new Jedis("192.168.31.59", 6379);
        /**
         *01.通过set方法 向redis中存放数据
         *02.在linux中查询是否插入数据
         */
        jedis.set("name", "xiaohei");

        /**
         * 从redis中获取数据
         */
        String value = jedis.get("name");
        System.out.println("name对应的value=" + value);

    }
}
