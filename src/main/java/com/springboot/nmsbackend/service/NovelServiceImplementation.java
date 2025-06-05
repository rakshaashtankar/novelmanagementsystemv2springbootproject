package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.repository.NovelsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@NoArgsConstructor
public class NovelServiceImplementation implements NovelsService{

    @Autowired
    private static NovelsRepository novelsRepository;

    public boolean addNovel(Novels novel) {
        boolean isAdded = false;
        try{
            Novels newNovel = new Novels();
            if(Objects.nonNull(novel.getNovelTitle()) && !"".equalsIgnoreCase(novel.getNovelTitle())) {
               newNovel.setNovelTitle(novel.getNovelTitle());
            }
            if(Objects.nonNull(novel.getNovelAuthor()) && !"".equalsIgnoreCase(novel.getNovelAuthor())) {
                newNovel.setNovelAuthor(novel.getNovelAuthor());
            }
            if(Objects.nonNull(novel.getNovelGenre()) && !"".equalsIgnoreCase(novel.getNovelGenre())) {
                newNovel.setNovelGenre(novel.getNovelGenre());
            }
            if(Objects.nonNull(novel.getNovelSynopsis()) && !"".equalsIgnoreCase(novel.getNovelSynopsis())) {
                newNovel.setNovelSynopsis(novel.getNovelSynopsis());
            }
            novelsRepository.save(newNovel);
            isAdded = true;
        } catch (NullPointerException e) {
            throw new NullPointerException("Incomplete data");
        }
        return isAdded;
    }
}
