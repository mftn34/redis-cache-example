package com.mtfn.redis.cache.example.service;

import com.mtfn.redis.cache.example.domain.entity.Store;
import com.mtfn.redis.cache.example.exception.StoreNotFoundException;
import com.mtfn.redis.cache.example.domain.repository.StoreRepository;
import lombok.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class StoreService implements Serializable {

    private final StoreRepository storeRepository;

    @Cacheable(value = "store-detail", key = "#storeCode",
            unless = "#result == null",
            cacheManager = "cacheManager1Hour")
    @Transactional(readOnly = true)
    //Since it was a simple example, there was no need to return dto
    public Store getStoreDetail(String storeCode) {
        return storeRepository.findByCode(storeCode).orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }
}
