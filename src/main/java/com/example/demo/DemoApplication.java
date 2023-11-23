package com.example.demo;

import java.util.List;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

  @ShellMethod("Saves a game record to Cloud Datastore: save-game <name> <score>")
  public String saveGame(String id, String name, double score) {
    UserRecord player = new UserRecord(id, name);
    GameRecord savedGame = this.gameRepository.save(new GameRecord(player, score));
    return savedGame.toString();
  }

  @ShellMethod("Loads all game records")
  public String findAllRecords() {
    Iterable<GameRecord> games = this.gameRepository.findAll();
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads records for a specific playerId")
  public String findRecordsById(String userId) {
    Iterable<GameRecord> games = this.gameRepository.findByPlayer_UserId(userId);
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads records for a player name")
  public String findRecordsByName(String name) {
    Iterable<GameRecord> games = this.gameRepository.findByPlayer_Name(name);
    return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads top 5 records by score")
  public String findTop5Score() {
    List<GameRecord> books = this.gameRepository.findTop5ByOrderByScoreDesc();
    return books.toString();
  }

  @ShellMethod("Removes all game records")
  public void removeAllRecords() {
     this.gameRepository.deleteAll();
  }
}
