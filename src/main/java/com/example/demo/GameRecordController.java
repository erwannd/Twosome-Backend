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
    this.gameRepository.save(record);
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

  @GetMapping("/findByPlayerId")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findByPlayerId(@RequestParam String playerId) {
    Iterable<GameRecord> records = this.gameRepository.findByPlayerId(playerId);
    List<GameRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @GetMapping("/findByName")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findByName(@RequestParam String name) {
    Iterable<GameRecord> records = this.gameRepository.findByName(name);
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
}