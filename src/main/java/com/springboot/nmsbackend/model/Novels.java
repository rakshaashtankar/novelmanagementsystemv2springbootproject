package com.springboot.nmsbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Novels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String novelTitle;

    @Column(nullable = false)
    private String novelAuthor;

    @Column(nullable = false)
    private String novelGenre;

    @Column(nullable = false)
    private String novelSynopsis;
}
