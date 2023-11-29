package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRecordController {
  private final UserRecordRepository playerRepository;

  public UserRecordController(UserRecordRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @GetMapping("/findNameById")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<UserRecord> findNameById(@RequestParam String googleId) {
    Iterable<UserRecord> records = this.playerRepository.findByUserId(googleId);
    List<UserRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }

  @GetMapping("/findAllPlayers")
  @ResponseBody
  @CrossOrigin(origins = "*")
  public List<UserRecord> findAllRecords() {
    Iterable<UserRecord> records = playerRepository.findAll();
    List<UserRecord> list = new ArrayList<>();
    records.forEach(list::add);
    return list;
  }
}
