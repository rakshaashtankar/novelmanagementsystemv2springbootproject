package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.repository.NovelsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
public class NovelServiceImplementation implements NovelsService{

    @Autowired
    private NovelsRepository novelsRepository;

    public Novels addNovel(Novels novel) {
        try {
            return novelsRepository.save(novel);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Duplicate novel entry. A novel with the same title, author, genre, and synopsis already exists.", e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public List<Novels> getAllNovels() {
        try {
            return novelsRepository.findAll();
        } catch (NullPointerException e ) {
            throw new RuntimeException("No novel entries in table. "+ e.getMessage());
        }
    }
}
