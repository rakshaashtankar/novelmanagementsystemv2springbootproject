package com.springboot.nmsbackend.repository;

import com.springboot.nmsbackend.model.Novels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelsRepository extends JpaRepository<Novels, Integer> {
    List<Novels> findByNovelTitle(String novelTitle);
}
