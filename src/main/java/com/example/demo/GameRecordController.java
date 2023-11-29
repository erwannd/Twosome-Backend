package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public String saveGame(@RequestBody CombinedRecord request) {
    if (request == null) {
      return "Invalid game record";
    }

    GameRecord record = request.getRecord();
    String googleId = record.getGoogleId();
    String newName = request.getName();

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

  @GetMapping("/findAllCombinedRecords")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<CombinedRecord> findAllCombinedRecords() {
    Iterable<GameRecord> gameRecords = gameRepository.findAll();
    Iterable<UserRecord> playerRecords = playerRepository.findAll();
    List<CombinedRecord> list = new ArrayList<>();
    for (GameRecord gameRecord : gameRecords) {
      for (UserRecord playerRecord : playerRecords) {
        if (Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId())) {
          list.add(new CombinedRecord(gameRecord, playerRecord.getName()));
        }
      }
    }
    return list;
  }

  @GetMapping("/findRecordsByPage")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public Page<GameRecord> findAllRecords(int page, int size, String sortField, Sort.Direction sortDirection) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));
    return gameRepository.findAll(pageable);
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

  @GetMapping("/findCombinedRecordsById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<CombinedRecord> findCombinedRecordsById(@RequestParam String userId) {
    Iterable<GameRecord> gameRecords = gameRepository.findByGoogleId(userId);
    Iterable<UserRecord> playerRecords = playerRepository.findAll();
    List<CombinedRecord> list = new ArrayList<>();
    for (GameRecord gameRecord : gameRecords) {
      for (UserRecord playerRecord : playerRecords) {
        if (Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId())) {
          list.add(new CombinedRecord(gameRecord, playerRecord.getName()));
        }
      }
    }
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

  /*ChatGPT recommendation for combining user and gameRecords
  @GetMapping("/findAllCombinedRecords")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<CombinedRecord> findAllCombinedRecords() {
    Iterable<GameRecord> gameRecords = gameRepository.findAll();
    Iterable<UserRecord> playerRecords = playerRepository.findAll();

    // Convert Iterable to List for efficient streaming
    List<GameRecord> gameRecordList = StreamSupport.stream(gameRecords.spliterator(), false)
            .collect(Collectors.toList());

    List<UserRecord> playerRecordList = StreamSupport.stream(playerRecords.spliterator(), false)
            .collect(Collectors.toList());

    List<CombinedRecord> combinedRecords = gameRecordList.stream()
            .filter(gameRecord -> playerRecordList.stream()
                    .anyMatch(playerRecord -> Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId())))
            .map(gameRecord -> {
              UserRecord matchingPlayerRecord = playerRecordList.stream()
                      .filter(playerRecord -> Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId()))
                      .findFirst()
                      .orElse(null);

              return new CombinedRecord(gameRecord, (matchingPlayerRecord != null) ? matchingPlayerRecord.getName() : null);
            })
            .collect(Collectors.toList());

    return combinedRecords;
  }*/
}