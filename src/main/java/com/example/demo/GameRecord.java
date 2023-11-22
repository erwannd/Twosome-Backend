package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "game_records")
public class GameRecord {
  @Id
  Long id;

  String playerId;

  String name;

  double score;

  public GameRecord(String playerId, String name, double score) {
    this.playerId = playerId;
    this.name = name;
    this.score = score;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
  	this.id=id;
  }

  public String getPlayerName() {
  	return this.name;
  }
  
  public void setPlayerName(String name) {
  	this.name=name;
  }
  
  public double getScore() {
  	return this.score;
  }
  
  public void setScore(double score) {
  	this.score=score;
  }

  @Override
  public String toString() {
    return "{" +
      "id:" + this.id +
      ", Name:'" + this.name + '\'' +
      ", playerId:'" + this.playerId + '\'' +
      ", Score:" + this.score + '}';
  }
}