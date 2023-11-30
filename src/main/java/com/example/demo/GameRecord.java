package com.example.demo;

import com.google.api.client.util.DateTime;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import com.google.cloud.spring.data.datastore.core.mapping.Unindexed;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Entity(name = "game_records")
public class GameRecord {
  @Id
  Long id;
  String googleId;
  double score;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMMM d, yyyy 'at' HH:mm z", locale = "en_US")
  Date playDate;

  public GameRecord(String googleId, double score) {
    this.googleId = googleId;
    this.score = score;
    this.playDate = null;
  }

  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
  	this.id=id;
  }

  public String getGoogleId() {
    return googleId;
  }

  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }

  public double getScore() {
  	return this.score;
  }
  
  public void setScore(double score) {
  	this.score=score;
  }

  public Date getPlayDate() {
    return playDate;
  }

  public void setPlayDate(Date playDate) {
    this.playDate = playDate;
  }

  public void prepareForSave() {
    if (playDate == null) {
      playDate = new Date();
    }
  }

  @Override
  public String toString() {
    return "{" +
            "id:" + this.id + ", googleId:" + this.googleId +
            ", Score:" + this.score + ", Date:" + this.playDate + '}';
  }
}