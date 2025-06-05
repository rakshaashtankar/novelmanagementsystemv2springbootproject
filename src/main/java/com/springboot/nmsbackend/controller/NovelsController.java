package com.springboot.nmsbackend.controller;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.service.NovelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Duplicate novel entry. A novel with the same title, author, genre, and synopsis already exists.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllNovels() {
        try {
            List<Novels> novelList = novelServiceImplementation.getAllNovels();
            return new ResponseEntity<>(novelList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNovelById(@PathVariable Integer id) {
        try {
            Novels novelById = novelServiceImplementation.getNovelById(id);
            return new ResponseEntity<>(novelById, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Novel does not exist by id " + id + " " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNovelById(@RequestBody Novels novel,@PathVariable Integer id ) {
        try {
            Novels updatedNovel = novelServiceImplementation.updateNovel(novel, id);
            return new ResponseEntity<>("Novel details update /n" + updatedNovel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Novel does not exist by id " + id + " " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
