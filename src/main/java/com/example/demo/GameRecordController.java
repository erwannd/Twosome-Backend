package com.example.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/findAllRecordsByPage")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public Page<GameRecord> findAllRecordsByPage(@RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "score") String sortField, @RequestParam(defaultValue = "desc") String sortDirection) {
    Sort.Direction direction = Sort.Direction.fromString(sortDirection);
    Sort sort = Sort.by(direction, sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    return gameRepository.findAll(pageable);
  }

//  @GetMapping("/findById")
//  @ResponseBody
//  @CrossOrigin(origins = "*")
//  public List<GameRecord> findById(@RequestParam String userId) {
//    Iterable<GameRecord> records = this.gameRepository.findByGoogleId(userId);
//    List<GameRecord> list = new ArrayList<>();
//    records.forEach(list::add);
//    return list;
//  }

  @GetMapping("/findRecordsByIdByPage")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public Page<GameRecord> findRecordsByIdByPage(@RequestParam String userId, @RequestParam int page, @RequestParam int size,  @RequestParam(defaultValue = "score") String sortField, @RequestParam(defaultValue = "desc") String sortDirection) {
    Sort.Direction direction = Sort.Direction.fromString(sortDirection);
    Sort sort = Sort.by(direction, sortField);
    Pageable pageable = PageRequest.of(page, size, sort);
    return gameRepository.findByGoogleId(userId, pageable);
  }

  @DeleteMapping("/deleteGameRecordById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public String deleteGameRecordById(@RequestParam Long recordId) {
    gameRepository.deleteById(recordId);
    return "Record with id '" + recordId + "' deleted successfully.";
  }

  /**
   * UserRecord repository method to fetch a user record
   * for a given google(firebase) id.
   */
  @GetMapping("/findUserNameById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<UserRecord> findUserNameById(@RequestParam String googleId) {
    Iterable<UserRecord> records = this.playerRepository.findByUserId(googleId);
    List<UserRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  /**
   * UserRecord repository method to find all user records.
   */
  @GetMapping("/findAllPlayers")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<UserRecord> findAllUserRecords() {
    Iterable<UserRecord> records = playerRepository.findAll();
    List<UserRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  /**
   * UserRecord repository method to partially update
   * user record fields.
   */
  @PatchMapping("/updateUserProfile/{userId}")
  @CrossOrigin(origins = "*")
  public ResponseEntity<UserRecord> updateUserProfile(
          @PathVariable String userId,
          @RequestBody UserRecord updatedUserRecord) {

    // Check if a user record with the given userId exists
    if (!playerRepository.existsByUserId(userId)) {
      return ResponseEntity.notFound().build();
    }

    // Retrieve the existing user record
    UserRecord existingUserRecord = playerRepository.findByUserId(userId).get(0);

    if (existingUserRecord != null) {
      // Update the name and profession fields
      if (updatedUserRecord.getName() != null) {
        existingUserRecord.setName(updatedUserRecord.getName());
      }

      // Save the updated user record
      playerRepository.save(existingUserRecord);
      return ResponseEntity.ok(existingUserRecord);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


//  @GetMapping("/findAllCombinedRecords")
//  @ResponseBody
//  @CrossOrigin(origins = "*")
//  public List<CombinedRecord> findAllCombinedRecords() {
//    Iterable<GameRecord> gameRecords = gameRepository.findAll();
//    Iterable<UserRecord> playerRecords = playerRepository.findAll();
//    List<CombinedRecord> list = new ArrayList<>();
//    for (GameRecord gameRecord : gameRecords) {
//      for (UserRecord playerRecord : playerRecords) {
//        if (Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId())) {
//          list.add(new CombinedRecord(gameRecord, playerRecord.getName()));
//        }
//      }
//    }
//    return list;
//  }

//  @GetMapping("/findCombinedRecordsById")
//  @ResponseBody
//  @CrossOrigin(origins = "*")
//  public List<CombinedRecord> findCombinedRecordsById(@RequestParam String userId) {
//    Iterable<GameRecord> gameRecords = gameRepository.findByGoogleId(userId);
//    Iterable<UserRecord> playerRecords = playerRepository.findAll();
//    List<CombinedRecord> list = new ArrayList<>();
//    for (GameRecord gameRecord : gameRecords) {
//      for (UserRecord playerRecord : playerRecords) {
//        if (Objects.equals(gameRecord.getGoogleId(), playerRecord.getUserId())) {
//          list.add(new CombinedRecord(gameRecord, playerRecord.getName()));
//        }
//      }
//    }
//    return list;
//  }

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