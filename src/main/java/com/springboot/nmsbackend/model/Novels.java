package com.springboot.nmsbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"novel_author", "novel_genre", "novel_synopsis", "novel_title"})})
public class Novels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "novel_title", nullable = false, length = 100)
    private String novelTitle;

    @Column(name = "novel_author", nullable = false, length = 100)
    private String novelAuthor;

    @Column(name = "novel_genre", nullable = false, length = 50)
    private String novelGenre;

    @Column(name = "novel_synopsis", nullable = false, length = 200)
    private String novelSynopsis;

}
