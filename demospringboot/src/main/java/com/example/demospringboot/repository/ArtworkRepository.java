package com.example.demospringboot.repository;

import com.example.demospringboot.entity.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    List<Artwork> findByArtistId(Long artistId);
    
    @Query("SELECT a FROM Artwork a WHERE a.price BETWEEN :minPrice AND :maxPrice")
    List<Artwork> findByPriceRange(@Param("minPrice") double minPrice, 
                                 @Param("maxPrice") double maxPrice);
                                 
    @NonNull Page<Artwork> findAll(@NonNull Pageable pageable);
}