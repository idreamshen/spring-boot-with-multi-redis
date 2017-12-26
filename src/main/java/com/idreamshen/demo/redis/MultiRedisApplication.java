package com.idreamshen.demo.redis;

import com.idreamshen.demo.redis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
@EnableCaching
public class MultiRedisApplication implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(MultiRedisApplication.class);

    private final StringRedisTemplate userStringRedisTemplate;
    private final StringRedisTemplate roleStringRedisTemplate;
    private final UserService userService;

    @Autowired
    public MultiRedisApplication(@Qualifier("userStringRedisTemplate") StringRedisTemplate userStringRedisTemplate,
                                 @Qualifier("roleStringRedisTemplate") StringRedisTemplate roleStringRedisTemplate,
                                 UserService userService) {
        this.userStringRedisTemplate = userStringRedisTemplate;
        this.roleStringRedisTemplate = roleStringRedisTemplate;
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MultiRedisApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        userStringRedisTemplate.opsForValue().set("hello", "world_1");
        roleStringRedisTemplate.opsForValue().set("hello", "world_2");

        String primaryKeyValue = userStringRedisTemplate.opsForValue().get("hello");
        String secondaryKeyValue = roleStringRedisTemplate.opsForValue().get("hello");

        logger.info("==================================================================");
        logger.info(String.format("read the primary redis, key is `hello`, value is %s", primaryKeyValue));
        logger.info(String.format("read the secondary redis, key is `hello`, value is %s", secondaryKeyValue));
        logger.info("==================================================================");

        // you can also check the value with redis-cli
    }

}
