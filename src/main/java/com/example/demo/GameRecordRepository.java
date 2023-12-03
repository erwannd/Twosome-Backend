package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
    List<GameRecord> findByGoogleId(String googleId);
    Page<GameRecord> findByGoogleId(@Param("googleId") String googleId, Pageable pageable);
}