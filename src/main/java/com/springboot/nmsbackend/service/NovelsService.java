package com.springboot.nmsbackend.service;

import com.springboot.nmsbackend.model.Novels;

import java.util.List;

public interface NovelsService {
    Novels addNovel(Novels novel);
    List<Novels> getAllNovels();
}
