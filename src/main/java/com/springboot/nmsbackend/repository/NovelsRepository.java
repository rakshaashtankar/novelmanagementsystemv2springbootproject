package com.springboot.nmsbackend.repository;

import com.springboot.nmsbackend.model.Novels;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelsRepository extends JpaRepository<Novels, Integer> {
}
