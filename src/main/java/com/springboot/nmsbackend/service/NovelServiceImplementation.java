package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.repository.NovelsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@NoArgsConstructor
public class NovelServiceImplementation implements NovelsService{

    @Autowired
    private NovelsRepository novelsRepository;

    public Novels addNovel(Novels novel) {
        try {
            return novelsRepository.save(novel);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
