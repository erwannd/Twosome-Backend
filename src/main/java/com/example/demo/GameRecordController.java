package com.example.demo;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class GameRecordController {
  private final GameRecordRepository gameRepository;
  private final UserRecordRepository playerRepository;

  public GameRecordController(GameRecordRepository gameRepository, UserRecordRepository playerRepository) {
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }

  @PostMapping("/saveGame")
  @CrossOrigin(origins = "*")
  public String saveGame(@RequestBody SaveGameRequest request) {
    if (request == null) {
      return "Invalid game record";
    }

    GameRecord record = request.getRecord();
    String googleId = record.getGoogleId();
    String newName = request.getNewName();

    List<UserRecord> existingPlayerRecord = playerRepository.findByUserId(googleId);

    if (!existingPlayerRecord.isEmpty()) {
      for (UserRecord rec : existingPlayerRecord) {
        rec.setName(newName);
      }
      playerRepository.saveAll(existingPlayerRecord);
    } else {
      playerRepository.save(new UserRecord(googleId, newName));
    }
    record.prepareForSave();
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

  @GetMapping("/findById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<GameRecord> findById(@RequestParam String userId) {
    Iterable<GameRecord> records = this.gameRepository.findByGoogleId(userId);
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