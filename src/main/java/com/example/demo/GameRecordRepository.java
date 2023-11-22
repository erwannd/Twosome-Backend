package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
  List<GameRecord> findByName(String name);
  List<GameRecord> findByPlayerId(String playerId);
  List<GameRecord> findTop5ByOrderByScoreDesc();
}