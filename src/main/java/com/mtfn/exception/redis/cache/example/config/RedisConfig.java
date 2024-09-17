package com.mtfn.exception.redis.cache.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
public class RedisConfig {

  @Bean
  RedisMessageListenerContainer keyExpirationListenerContainer(RedisConnectionFactory connectionFactory,
                                                               RedisExpireEventListener expirationListener) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(expirationListener, new PatternTopic("__keyevent@*__:expired"));
    container.setErrorHandler(e -> log.error("There was an error in Redis key expiration listener container", e));
    return container;
  }

  @Bean(value = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
    redisTemplate.setEnableTransactionSupport(true);
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }

  @Primary
  @Bean(name = "cacheManager") // Default cache manager is infinite
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory)
                            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                            .build();
  }

  @Bean(name = "cacheManager1Hour")
  public CacheManager cacheManager1Hour(RedisConnectionFactory redisConnectionFactory) {
    Duration expiration = Duration.ofHours(1);
    return RedisCacheManager.builder(redisConnectionFactory)
                            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(expiration))
                            .build();
  }

  @Bean(name = "cacheManager1Minute")
  public CacheManager cacheManager1Minute(RedisConnectionFactory redisConnectionFactory) {
    Duration expiration = Duration.ofMinutes(1);
    return RedisCacheManager.builder(redisConnectionFactory)
                            .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(expiration))
                            .build();
  }
}
