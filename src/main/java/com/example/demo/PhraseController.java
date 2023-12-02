package com.example.demo;

import com.example.demo.Phrase;
import com.example.demo.PhraseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class PhraseController {
    private final PhraseRepository phraseRepository;

    public PhraseController(PhraseRepository phraseRepository) {
        this.phraseRepository = phraseRepository;
    }

    @GetMapping("/findByCategory")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String findByCategory(@RequestParam String category) {
        // Retrieve all phrases for the specified category
        List<Phrase> phrases = phraseRepository.findByCategory(category);
        if (phrases.isEmpty()) {
            return "No phrases found for the specified category";
        }
        // Select a random phrase from the list
        Random random = new Random();
        Phrase randomPhrase = phrases.get(random.nextInt(phrases.size()));

        // Return the selected phrase
        return randomPhrase.getPhrase();
    }

    @PostMapping("/addPhrase")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String addPhrase(@RequestBody Phrase newPhrase) {
        phraseRepository.save(newPhrase);
        return "Phrase added successfully!";
    }

    @GetMapping("/getHint")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public String getHint(@RequestParam Long phraseId) {
        // Retrieve the phrase from the datastore based on the provided phraseId
        Phrase phrase = phraseRepository.findById(phraseId).orElse(null);
        // Check if the phrase exists
        if (phrase == null) {
            return "Phrase not found for the given ID";
        }
        // Return the hint for the selected phrase
        return phrase.getHint();
    }
}