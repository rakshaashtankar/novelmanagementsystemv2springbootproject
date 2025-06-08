package com.springboot.nmsbackend.repository;

import com.springboot.nmsbackend.model.Novels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelsRepository extends JpaRepository<Novels, Integer> {

    @Query("SELECT n FROM Novels n WHERE " +
            "LOWER(n.novelAuthor) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(n.novelGenre) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(n.novelSynopsis) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(n.novelTitle) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "ORDER BY n.id")
    Page<Novels> findBySearchText(@Param("searchText") String searchText, Pageable pageable);

}
