package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;

public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
  List<GameRecord> findByPlayer_Name(String name);
  List<GameRecord> findByPlayer_PlayerId(String playerId);
  List<GameRecord> findTop5ByOrderByScoreDesc();
}