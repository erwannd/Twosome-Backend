package com.example.demo;

public class SaveGameRequest {
  private GameRecord record;
  private String newName;

  public SaveGameRequest(GameRecord record, String newName) {
    this.record = record;
    this.newName = newName;
  }

  public GameRecord getRecord() {
    return record;
  }

  public void setRecord(GameRecord record) {
    this.record = record;
  }

  public String getNewName() {
    return newName;
  }

  public void setNewName(String newName) {
    this.newName = newName;
  }
}
