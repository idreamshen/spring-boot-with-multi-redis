package com.idreamshen.demo.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Cacheable(cacheNames = "user", cacheManager = "userCacheManager")
    public int getUser(int id) {
        logger.info("return user id=" + id);
        return id;
    }

    @Override
    @Cacheable(cacheNames = "role", cacheManager = "roleCacheManager")
    public int getRole(int id) {
        logger.info("return role id=" + id);
        return id;
    }
}
