package com.mtfn.exception.redis.cache.example.domain.repository;

import com.mtfn.exception.redis.cache.example.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByCode(String code);
}