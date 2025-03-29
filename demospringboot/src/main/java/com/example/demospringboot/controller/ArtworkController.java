package com.example.demospringboot.controller;

import com.example.demospringboot.entity.Artwork;
import com.example.demospringboot.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
public class ArtworkController {

        @Autowired
    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public ResponseEntity<Page<Artwork>> getAllArtworks(Pageable pageable) {
        return ResponseEntity.ok(artworkService.getAllArtworks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artwork> getArtworkById(@PathVariable Long id) {
        return ResponseEntity.ok(artworkService.getArtworkById(id));
    }

    @PostMapping
    public ResponseEntity<Artwork> createArtwork(@RequestBody Artwork artwork, 
                                                @RequestParam Long artistId) {
        return ResponseEntity.ok(artworkService.createArtwork(artwork, artistId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artwork> updateArtwork(@PathVariable Long id, @RequestBody Artwork artworkDetails) {
        return ResponseEntity.ok(artworkService.updateArtwork(id, artworkDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Artwork>> getArtworksByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(artworkService.getArtworksByArtist(artistId));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Artwork>> getArtworksByPriceRange(
            @RequestParam double minPrice, 
            @RequestParam double maxPrice) {
        return ResponseEntity.ok(artworkService.getArtworksByPriceRange(minPrice, maxPrice));
    }
}