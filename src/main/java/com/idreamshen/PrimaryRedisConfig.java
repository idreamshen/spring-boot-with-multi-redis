package com.idreamshen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class PrimaryRedisConfig {

    @Value("${spring.redis.primary.host}")
    private String host;

    @Value("${spring.redis.primary.port}")
    private int port;

    @Value("${spring.redis.primary.database}")
    private int database;

    @Primary
    @Bean(name = "primaryRedisConnectionFactory")
    public RedisConnectionFactory primaryRedisConnectionFactory() {

        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(host);
        redisConnectionFactory.setPort(port);
        redisConnectionFactory.setDatabase(database);
        return redisConnectionFactory;
    }

    @Bean(name = "primaryStringRedisTemplate")
    public StringRedisTemplate primaryRedisTemplate(@Qualifier("primaryRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

}
