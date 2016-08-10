package com.idreamshen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class MultiRedisApplication implements CommandLineRunner {

	private final static Logger logger = LoggerFactory.getLogger(MultiRedisApplication.class);

	@Autowired
	@Qualifier("primaryStringRedisTemplate")
	private StringRedisTemplate primaryStringRedisTemplate;

	@Autowired
	@Qualifier("secondaryStringRedisTemplate")
	private StringRedisTemplate secondaryStringRedisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MultiRedisApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		primaryStringRedisTemplate.opsForValue().set("hello", "world_1");
		secondaryStringRedisTemplate.opsForValue().set("hello", "world_2");

		String primaryKeyValue = primaryStringRedisTemplate.opsForValue().get("hello");
		String secondaryKeyValue = secondaryStringRedisTemplate.opsForValue().get("hello");

		logger.info("==================================================================");
		logger.info(String.format("read the primary redis, key is `hello`, value is %s", primaryKeyValue));
		logger.info(String.format("read the secondary redis, key is `hello`, value is %s", secondaryKeyValue));
		logger.info("==================================================================");

		// you can also check the value with redis-cli

	}
}
