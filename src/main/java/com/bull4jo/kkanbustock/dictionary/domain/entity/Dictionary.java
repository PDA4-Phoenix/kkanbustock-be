package com.bull4jo.kkanbustock.dictionary.domain.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stockkeyword")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String explanation;

    public Dictionary(String word, String explanation) {
        this.word = word;
        this.explanation = explanation;
    }
}
