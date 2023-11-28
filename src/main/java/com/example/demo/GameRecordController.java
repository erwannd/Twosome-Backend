package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class GameRecordController {
  private final GameRecordRepository gameRepository;

  public GameRecordController(GameRecordRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @PostMapping("/saveGame")
  @CrossOrigin(origins = "*")
  public String saveGame(@RequestBody GameRecord record) {
    if (record == null) {
      return "Invalid game record";
    }

    String userId = record.getPlayer().getUserId();
    String newName = record.getPlayer().getName();

    List<GameRecord> existingRecords = gameRepository.findByPlayer_UserId(userId);

    if (!existingRecords.isEmpty()) {
      for (GameRecord game : existingRecords) {
        game.getPlayer().setName(newName);
      }
      gameRepository.saveAll(existingRecords);
    }
    gameRepository.save(record);
    return "success";
  }

  @GetMapping("/findAllRecords")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findAllRecords() {
  	Iterable<GameRecord> records = this.gameRepository.findAll();
    List<GameRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @GetMapping("/findByName")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findByName(@RequestParam String name) {
    Iterable<GameRecord> records = this.gameRepository.findByPlayer_Name(name);
    List<GameRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @GetMapping("/findById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findById(@RequestParam String userId) {
    Iterable<GameRecord> records = this.gameRepository.findByPlayer_UserId(userId);
    List<GameRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @GetMapping("/findTopFiveScore")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findTopFiveScore() {
    Iterable<GameRecord> records = this.gameRepository.findTop5ByOrderByScoreDesc();
    List<GameRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @DeleteMapping("/deleteByRecordId")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public String deleteByRecordId(@RequestParam Long recordId) {
    gameRepository.deleteById(recordId);
    return "Record with id '" + recordId + "' deleted successfully.";
  }
}