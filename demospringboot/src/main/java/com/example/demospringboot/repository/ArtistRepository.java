package com.example.demospringboot.repository;

import com.example.demospringboot.entity.Artist;

import org.springframework.lang.NonNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByEmail(String email);
    
    @Query("SELECT a FROM Artist a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Artist> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @NonNull Page<Artist> findAll(@NonNull Pageable pageable);
}