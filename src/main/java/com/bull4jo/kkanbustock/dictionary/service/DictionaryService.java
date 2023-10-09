package com.bull4jo.kkanbustock.dictionary.service;

import com.bull4jo.kkanbustock.dictionary.domain.entity.Dictionary;
import com.bull4jo.kkanbustock.dictionary.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {
        return dictionaryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Dictionary> findById(Long id) {
        return dictionaryRepository.findById(id);
    }

}
