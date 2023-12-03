package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import java.util.List;

public interface UserRecordRepository extends DatastoreRepository<UserRecord, String> {
  List<UserRecord> findByUserId(String userId);
  boolean existsByUserId(String userId);
}
