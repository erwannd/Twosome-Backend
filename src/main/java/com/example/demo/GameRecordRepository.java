package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import com.google.cloud.spring.data.datastore.repository.query.Query;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.query.Param;

  public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {

    List<GameRecord> findByPlayer_Name(String name);

    List<GameRecord> findByPlayer_UserId(String userId);

    List<GameRecord> findTop5ByOrderByScoreDesc();
}