package com.bull4jo.kkanbustock.dictionary.repository;

import com.bull4jo.kkanbustock.dictionary.domain.entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

}
