package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;

@Entity(name = "game_records")
public class GameRecord {
  @Id
  Long id;
  UserRecord player;
  double score;

  public GameRecord(UserRecord player, double score) {
    this.player = player;
    this.score = score;
  }

  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
  	this.id=id;
  }

  public UserRecord getPlayer() {
    return this.player;
  }

  public void setPlayer(UserRecord player) {
    this.player = player;
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
            "id:" + this.id + ", player:" + this.player +
            ", Score:" + this.score + '}';
  }
}