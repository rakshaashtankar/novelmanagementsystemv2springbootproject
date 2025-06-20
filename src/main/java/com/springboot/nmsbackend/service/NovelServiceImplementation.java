package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import com.springboot.nmsbackend.repository.NovelsRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Objects;

@Service
@NoArgsConstructor
@CrossOrigin(origins = "*")
public class NovelServiceImplementation implements NovelsService{

    @Autowired
    private NovelsRepository novelsRepository;

    @Override
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
    public Page<Novels> getAllNovels(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            return novelsRepository.findAll(pageable);
        } catch (RuntimeException e ) {
            throw new RuntimeException("No novel entries in table. "+ e.getMessage());
        }
    }

    @Override
    public Novels getNovelById(Integer id) {
        return novelsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Novel with id " + id + " does not exist."));
    }

    @Override
    public Novels updateNovel(Novels novel, Integer id) {
        try {
            Novels updatedNovel = novelsRepository.findById(id).get();
            if (Objects.nonNull(novel.getNovelTitle()) && !"".equalsIgnoreCase(novel.getNovelTitle()) && !novel.getNovelTitle().equals(updatedNovel.getNovelTitle())) {
                updatedNovel.setNovelTitle(novel.getNovelTitle());
            }
            if (Objects.nonNull(novel.getNovelAuthor()) && !"".equalsIgnoreCase(novel.getNovelAuthor()) && !novel.getNovelAuthor().equals(updatedNovel.getNovelAuthor())) {
                updatedNovel.setNovelAuthor(novel.getNovelAuthor());
            }
            if (Objects.nonNull(novel.getNovelGenre()) && !"".equalsIgnoreCase(novel.getNovelGenre()) && !novel.getNovelGenre().equals(updatedNovel.getNovelGenre())) {
                updatedNovel.setNovelGenre(novel.getNovelGenre());
            }
            if (Objects.nonNull(novel.getNovelSynopsis()) && !"".equalsIgnoreCase(novel.getNovelSynopsis()) && !novel.getNovelSynopsis().equals(updatedNovel.getNovelSynopsis())) {
                updatedNovel.setNovelSynopsis(novel.getNovelSynopsis());
            }
            novelsRepository.save(updatedNovel);
            return updatedNovel;
        } catch (RuntimeException e) {
            throw new RuntimeException("Novel with id " + id + " does not exist. ");
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try{
            if(novelsRepository.findById(id).isPresent()) {
                novelsRepository.deleteById(id);
                return true;
            }
            return false;
        }catch (RuntimeException e) {
            throw new RuntimeException("Novel with id " + id + " does not exist. " + e.getMessage());
        }
    }

    @Override
    public Page<Novels> findBySearchTerm(String searchTerm, Pageable pageable) {
        try {
            return novelsRepository.findBySearchText(searchTerm, pageable);
        } catch (RuntimeException e ) {
            throw new RuntimeException("No novel entries in table. "+ e.getMessage());
        }
    }
}
