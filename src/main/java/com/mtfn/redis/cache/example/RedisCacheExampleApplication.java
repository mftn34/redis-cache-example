package com.mtfn.redis.cache.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCaching
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP,
		keyspaceNotificationsConfigParameter = "")
public class RedisCacheExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisCacheExampleApplication.class, args);
	}

}
