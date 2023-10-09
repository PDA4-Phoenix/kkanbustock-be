package com.bull4jo.kkanbustock.dictionary.controller;

import com.bull4jo.kkanbustock.dictionary.domain.entity.Dictionary;
import com.bull4jo.kkanbustock.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dictionary")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping
    public ResponseEntity<List<Dictionary>> getAllDictionaries() {
        return ResponseEntity.ok(dictionaryService.findAll());
    }

    @GetMapping("/{dictionaryId}")
    public ResponseEntity<Dictionary> getDictionaryById(@PathVariable Long dictionaryId) {
        Optional<Dictionary> dictionary = dictionaryService.findById(dictionaryId);
        if (dictionary.isPresent()) {
            return ResponseEntity.ok(dictionary.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
