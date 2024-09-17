package com.mtfn.exception.handler.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisExpireEventListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String[] splitMessage = message.toString().split(":");
        if (splitMessage.length > 1) {
            String hashKey = message.toString().split(":")[0];
            log.info("Key expired. Key is{}", hashKey);
        }

        log.info("Received expire event for body={}", new String(message.getBody()));
    }
}
