package com.suddenly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class JavaReptileApplicationTests {

    private Logger logger = LogManager.getLogger(getClass());

    @Value("${server.port}")
    private String port;

    @Value("${MyTest}")
    private String test;

    @Test
    void contextLoads() {
        System.out.println(port);
        System.out.println(test);

        logger.warn(port);
        logger.error(test);
    }

    @Test
    void redisTest() {
        Jedis jedis = new Jedis("101.133.174.185", 6379);
        jedis.auth("98100815");
        System.out.println(jedis.ping());
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.hkeys("user"));
        System.out.println(jedis.hvals("user"));
    }

}
