package com.bull4jo.kkanbustock.news.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String link;

    @Column
    private String description;

    @Column
    private String pubDate;

    @Builder
    public News(Long id, String title, String link, String description, String pubDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
    }

}
