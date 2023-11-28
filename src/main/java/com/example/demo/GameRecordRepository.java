package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
    List<GameRecord> findByPlayer_Name(String name);
    List<GameRecord> findByPlayer_UserId(String userId);
    List<GameRecord> findTop5ByOrderByScoreDesc();
    Page<GameRecord> findByPlayer_Name(String name, Pageable pageable);
    Page<GameRecord> findByPlayer_UserId(String userId, Pageable pageable);
}