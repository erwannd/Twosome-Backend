package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
    List<GameRecord> findByGoogleId(String googleId);
    List<GameRecord> findTop5ByOrderByScoreDesc();
}