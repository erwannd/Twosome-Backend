package com.example.demo;
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhraseRepository extends DatastoreRepository<Phrase, Long> {
   List<Phrase> findByCategory(String category);

}