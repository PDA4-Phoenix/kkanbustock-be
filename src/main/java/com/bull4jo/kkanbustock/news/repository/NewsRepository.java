package com.bull4jo.kkanbustock.news.repository;

import com.bull4jo.kkanbustock.news.domain.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query(value = "SELECT * FROM news ORDER BY RAND()", nativeQuery = true)
    Page<News> findAll(Pageable pageable);
}
