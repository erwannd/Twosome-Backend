package com.example.demo;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "user_records")
public class UserRecord {
  @Id
  String playerId;
  String name;

  public UserRecord(String playerId, String name) {
    this.playerId = playerId;
    this.name = name;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "<" +
            "playerId='" + playerId + '\'' +
            ", name='" + name + '\'' +
            '>';
  }
}
