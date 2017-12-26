package com.idreamshen.demo.redis.config;

import com.idreamshen.demo.redis.property.RoleRedisProperty;
import com.idreamshen.demo.redis.property.UserRedisProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Autowired
    private UserRedisProperty userRedisProperty;

    @Autowired
    private RoleRedisProperty roleRedisProperty;

    @Primary
    @Bean(name = "userRedisConnectionFactory")
    public RedisConnectionFactory userRedisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(userRedisProperty.getHost());
        redisConnectionFactory.setPort(userRedisProperty.getPort());
        redisConnectionFactory.setDatabase(userRedisProperty.getDatabase());
        return redisConnectionFactory;
    }

    @Bean(name = "userStringRedisTemplate")
    public StringRedisTemplate userStringRedisTemplate(@Qualifier("userRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

    @Bean(name = "userRedisTemplate")
    public RedisTemplate userRedisTemplate(@Qualifier("userRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        setSerializer(stringRedisTemplate);
        return stringRedisTemplate;
    }

    @Bean(name = "roleRedisConnectionFactory")
    public RedisConnectionFactory roleRedisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(roleRedisProperty.getHost());
        redisConnectionFactory.setPort(roleRedisProperty.getPort());
        redisConnectionFactory.setDatabase(roleRedisProperty.getDatabase());
        return redisConnectionFactory;
    }

    @Bean(name = "roleStringRedisTemplate")
    public StringRedisTemplate roleStringRedisTemplate(@Qualifier("roleRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }

    @Bean(name = "roleRedisTemplate")
    public RedisTemplate roleRedisTemplate(@Qualifier("roleRedisConnectionFactory") RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        setSerializer(stringRedisTemplate);
        return stringRedisTemplate;
    }

    private void setSerializer(RedisTemplate<String, String> template) {
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    }

}
