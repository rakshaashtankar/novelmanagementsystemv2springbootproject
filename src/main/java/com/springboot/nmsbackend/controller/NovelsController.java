package com.springboot.nmsbackend.controller;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.service.NovelServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
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
    public ResponseEntity<?> getNovels(
            @RequestParam(defaultValue = "0") int page,       // which page, default to 0
            @RequestParam(name = "size", defaultValue = "5") int size  // number of rows per page
    ) {
        try {

            Page<Novels> novelList = novelServiceImplementation.getAllNovels(page, size);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNovelById(@RequestBody Novels novel,@PathVariable Integer id ) {
        try {
            Novels updatedNovel = novelServiceImplementation.updateNovel(novel, id);
            return new ResponseEntity<>("Novel details update for id " +id +" .", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Novel does not exist by id " + id + " " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteNovelById(@PathVariable Integer id) {
        try {
            boolean isDeleted = novelServiceImplementation.deleteById(id);
            if(isDeleted) {
                return new ResponseEntity<>("Novel details deleted for id " +id +" .", HttpStatus.OK);
            }
            return new ResponseEntity<>("Error deleteing novel details with id " +id+ " .", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Novel does not exist by id " + id + " " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findBySearchTerm(@RequestParam("term") String term,
            @RequestParam(defaultValue = "0") int page ,
            @RequestParam(defaultValue = "5") int size) {
        if (term == null || term.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Search term cannot be empty");
        }

        try {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
                Page<Novels> novelList = novelServiceImplementation.findBySearchTerm(term.trim().toLowerCase(), pageable);

            if (novelList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No novels found matching '" + term + "'");
            }

            return ResponseEntity.ok(novelList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error processing search: " + e.getMessage());
        }
    }


}
