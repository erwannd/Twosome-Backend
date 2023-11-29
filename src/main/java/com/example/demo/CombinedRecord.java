package com.example.demo;

public class CombinedRecord {
  private GameRecord record;
  private String name;

  public CombinedRecord(GameRecord record, String name) {
    this.record = record;
    this.name = name;
  }

  public GameRecord getRecord() {
    return record;
  }

  public void setRecord(GameRecord record) {
    this.record = record;
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
            "record=" + record +
            ", name='" + name + '\'' +
            '}';
  }
}
