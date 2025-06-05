package com.springboot.nmsbackend.controller;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.service.NovelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/novels")
public class NovelsController {

    @Autowired
    private NovelServiceImplementation novelServiceImplementation;

    @PostMapping
    public ResponseEntity<?> addNovel(@RequestBody Novels novel) {
        try{
            if(Objects.nonNull(novel.getNovelTitle()) && !"".equalsIgnoreCase(novel.getNovelTitle())
                    && Objects.nonNull(novel.getNovelAuthor()) && !"".equalsIgnoreCase(novel.getNovelAuthor())
                    && Objects.nonNull(novel.getNovelGenre()) && !"".equalsIgnoreCase(novel.getNovelGenre())
                    && Objects.nonNull(novel.getNovelSynopsis()) && !"".equalsIgnoreCase(novel.getNovelSynopsis())
            ) {
                Novels newNovel = novelServiceImplementation.addNovel(novel);
                return new ResponseEntity<>("New novel added successfully with id " +newNovel.getId(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Insufficient data.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
