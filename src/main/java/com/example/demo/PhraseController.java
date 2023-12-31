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

    @GetMapping("/getRandomPhraseByCategory")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public Phrase getRandomPhraseByCategory(@RequestParam String category) {
        // Retrieve all phrases for the specified category
        List<Phrase> phrases = phraseRepository.findByCategory(category);
        // Select a random phrase from the list
        Random random = new Random();
        Phrase randomPhrase = phrases.get(random.nextInt(phrases.size()));

        // Return the selected phrase
        return randomPhrase;
    }

    @GetMapping("/findAllPhrases")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<Phrase> findAllPhrases() {
        Iterable<Phrase> records = phraseRepository.findAll();
        List<Phrase> list = new ArrayList<>();
        records.forEach(list::add);
        return list;
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