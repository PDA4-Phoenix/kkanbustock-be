package com.bull4jo.kkanbustock.dictionary.controller;

import com.bull4jo.kkanbustock.dictionary.domain.entity.Dictionary;
import com.bull4jo.kkanbustock.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@ControllerAdvice
@RestController
@RequestMapping("/api")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/v1/dictionary")
    public ResponseEntity<List<Dictionary>> getAllDictionaries() {
        return ResponseEntity.ok(dictionaryService.findAll());
    }

    @GetMapping("/v1/dictionary/{dictionaryId}")
    public ResponseEntity<Dictionary> getDictionaryById(@PathVariable Long dictionaryId) {
        Dictionary dictionary = dictionaryService.findById(dictionaryId);
        return ResponseEntity.ok(dictionary);
    }

}
