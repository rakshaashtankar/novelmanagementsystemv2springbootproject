package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;
import org.springframework.data.domain.Page;


import java.util.List;

public interface NovelsService {
    Novels addNovel(Novels novel);
    Page<Novels> getAllNovels(int page, int size);
    Novels getNovelById(Integer id);
    Novels updateNovel(Novels novel, Integer id);
    boolean deleteById(Integer id);
    List<Novels> findBySearchTerm(String searchTerm);
}
