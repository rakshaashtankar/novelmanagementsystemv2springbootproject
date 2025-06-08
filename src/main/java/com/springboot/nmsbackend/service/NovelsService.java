package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface NovelsService {
    Novels addNovel(Novels novel);
    Page<Novels> getAllNovels(int page, int size);
    Novels getNovelById(Integer id);
    Novels updateNovel(Novels novel, Integer id);
    boolean deleteById(Integer id);
    Page<Novels> findBySearchTerm(String searchTerm, Pageable pageable);
}
