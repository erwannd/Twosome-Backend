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
  public String saveGame(String playerId, String name, double score) {
     GameRecord savedGame = this.gameRepository.save(new GameRecord(playerId, name, score));
     return savedGame.toString();
  }

  @ShellMethod("Loads all game records")
  public String findAllRecords() {
     Iterable<GameRecord> games = this.gameRepository.findAll();
     return Lists.newArrayList(games).toString();
  }

  @ShellMethod("Loads records by player name: find-by-name <name>")
  public String findByName(String name) {
     List<GameRecord> books = this.gameRepository.findByName(name);
     return books.toString();
  }

  @ShellMethod("Loads records by player id: find-by-player-id <playerId>")
  public String findByPlayerId(String playerId) {
    List<GameRecord> books = this.gameRepository.findByPlayerId(playerId);
    return books.toString();
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
