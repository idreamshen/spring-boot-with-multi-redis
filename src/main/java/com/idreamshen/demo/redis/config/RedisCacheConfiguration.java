package com.idreamshen.demo.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@EnableCaching
@Configuration
public class RedisCacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    @Qualifier("userRedisTemplate")
    private RedisTemplate userRedisTemplate;

    @Autowired
    @Qualifier("roleRedisTemplate")
    private RedisTemplate roleRedisTemplate;

    @Primary
    @Bean(name = "userCacheManager")
    public CacheManager userCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(userRedisTemplate);
        redisCacheManager.setDefaultExpiration(3600);
        return redisCacheManager;
    }

    @Bean(name = "roleCacheManager")
    public CacheManager roleCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(roleRedisTemplate);
        redisCacheManager.setDefaultExpiration(3600);
        return redisCacheManager;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
