package com.example.demo;

import java.util.List;
import java.util.Random;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.bind.annotation.*;

@ShellComponent
@SpringBootApplication
public class DemoApplication {
  @Autowired
  GameRecordRepository gameRepository;
  @Autowired
  UserRecordRepository playerRepository;

  @Autowired
  PhraseRepository phraseRepository;

  public static void main(String[] args) {
     SpringApplication.run(DemoApplication.class, args);
  }

  @ShellMethod("Saves and updates records: <id> <name> <score>")
  public String saveAndUpdateRecord(String id, String name, double score) {
    List<UserRecord> existingRecords = playerRepository.findByUserId(id);

    if (!existingRecords.isEmpty()) {
      for (UserRecord existingRecord : existingRecords) {
        existingRecord.setName(name);
      }
      playerRepository.saveAll(existingRecords);
    } else {
      playerRepository.save(new UserRecord(id, name));
    }
    GameRecord record = new GameRecord(id, score);
    record.prepareForSave();
    GameRecord savedGame = this.gameRepository.save(record);
    return savedGame.toString();
  }

  @ShellMethod("Loads all game records")
  public String findAllRecords() {
    Iterable<GameRecord> games = this.gameRepository.findAll();
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Find all records with pagination <page> <size>")
  public String findAllRecordsByPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<GameRecord> games = this.gameRepository.findAll(pageable);
    return games.toString();
  }

  @ShellMethod("Loads records for a particular google id <id>")
  public String findById(String userId) {
    Iterable<GameRecord> games = this.gameRepository.findByGoogleId(userId);
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Find records by googleId with pagination <id> <page> <size>")
  public String findRecordsByIdByPage(String id, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<GameRecord> games = this.gameRepository.findByGoogleId(id, pageable);
    return games.toString();
  }

  @ShellMethod("Get all players")
  public String findAllPlayers() {
    Iterable<UserRecord> games = playerRepository.findAll();
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads top 5 records by score")
  public String findTop5Score() {
    List<GameRecord> books = this.gameRepository.findTop5ByOrderByScoreDesc();
    return books.toString();
  }

  @ShellMethod("Delete game record based on record id")
  public void deleteByRecordId(Long recordId) {
    gameRepository.deleteById(recordId);
  }

  @ShellMethod("Removes all game records")
  public void removeAllRecords() {
     this.gameRepository.deleteAll();
  }

  @ShellMethod("Returns a random phrase from a category: <category>")
  public String findByCategory(@RequestParam String category) {
    // Retrieve all phrases for the specified category
    List<Phrase> phrases = phraseRepository.findByCategory(category);
    if (phrases.isEmpty()) {
      return "No phrases found for the specified category";
    }
    // Select a random phrase from the list
    Random random = new Random();
    Phrase randomPhrase = phrases.get(random.nextInt(phrases.size()));

    // Return the selected phrase
    return randomPhrase.getPhrase();
  }

  @ShellMethod("Retrieves the hint for phrase: <phraseId>")
  public String getHint(@RequestParam Long phraseId) {
    // Retrieve the phrase from the datastore based on the provided phraseId
    Phrase phrase = phraseRepository.findById(phraseId).orElse(null);
    // Check if the phrase exists
    if (phrase == null) {
      return "Phrase not found for the given ID";
    }
    // Return the hint for the selected phrase
    return phrase.getHint();
  }

  @ShellMethod
  public String addPhrase(@RequestBody Phrase newPhrase) {
    phraseRepository.save(newPhrase);
    return "Phrase added successfully!";
  }


}
