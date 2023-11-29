package com.example.demo;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "user_records")
public class UserRecord {
  @Id
  Long id;
  String userId;
  String name;

  public UserRecord(String userId, String name) {
    this.userId = userId;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
