package com.bull4jo.kkanbustock.dictionary.service;

import com.bull4jo.kkanbustock.dictionary.domain.entity.Dictionary;
import com.bull4jo.kkanbustock.dictionary.repository.DictionaryRepository;
import com.bull4jo.kkanbustock.exception.CustomException;
import com.bull4jo.kkanbustock.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {

        return dictionaryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Dictionary findById(Long id) {
        return dictionaryRepository.findById(id)
                .orElseThrow(() ->  new CustomException(ErrorCode.DICTIONARY_NOT_FOUND));
    }
}
