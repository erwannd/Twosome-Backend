package com.example.demo;

import java.util.List;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.StructuredQuery;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@SpringBootApplication
public class DemoApplication {
  @Autowired
  GameRecordRepository gameRepository;

  public static void main(String[] args) {
     SpringApplication.run(DemoApplication.class, args);
  }

  @ShellMethod("Saves and updates records: <id> <name> <score>")
  public String saveAndUpdateRecords(String id, String name, double score) {
    List<GameRecord> existingRecords = gameRepository.findByPlayer_UserId(id);

    if (!existingRecords.isEmpty()) {
      for (GameRecord existingRecord : existingRecords) {
        existingRecord.getPlayer().setName(name);
      }
      gameRepository.saveAll(existingRecords);
    }

    UserRecord player = new UserRecord(id, name);
    GameRecord savedGame = this.gameRepository.save(new GameRecord(player, score));
    return savedGame.toString();
  }

  @ShellMethod("Loads all game records")
  public String findAllRecords() {
    Iterable<GameRecord> games = this.gameRepository.findAll();
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Pages all game records")
  public String findRecordsByPage(String field) {
    Pageable pageable = PageRequest.of(0,2, Sort.by(Sort.Direction.ASC, field));
    Iterable<GameRecord> games = this.gameRepository.findAll(pageable);
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads records for a player name <name>")
  public String findByName(String name) {
    Iterable<GameRecord> games = this.gameRepository.findByPlayer_Name(name);
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads records for a particular google id <id>")
  public String findById(String userId) {
    Iterable<GameRecord> games = this.gameRepository.findByPlayer_UserId(userId);
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
}
