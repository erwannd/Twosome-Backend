package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

@Entity(name = "phrase")
public class Phrase{
    @Id
    Long id;
    String phrase;
    String category;
    String hint;

    public Phrase(String phrase, String category, String hint) {
        this.phrase = phrase;
        this.category = category;
        this.hint = hint;
    }



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String googleId) {
        this.phrase = phrase;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + this.id + ", Phrase:" + this.phrase+
                ", Score:" + this.category+ ", Date:" + this.hint + '}';
    }
}