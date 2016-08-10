package com.idreamshen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class SecondaryRedisConfig {

    @Value("${spring.redis.secondary.host}")
    private String host;

    @Value("${spring.redis.secondary.port}")
    private int port;

    @Value("${spring.redis.secondary.database}")
    private int database;

    @Bean(name = "secondaryRedisConnectionFactory")
    public RedisConnectionFactory secondaryRedisConnectionFactory() {

        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(host);
        redisConnectionFactory.setPort(port);
        redisConnectionFactory.setDatabase(database);
        return redisConnectionFactory;
    }

    @Bean(name = "secondaryStringRedisTemplate")
    public StringRedisTemplate secondaryStringRedisTemplate(@Qualifier("secondaryRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

}
