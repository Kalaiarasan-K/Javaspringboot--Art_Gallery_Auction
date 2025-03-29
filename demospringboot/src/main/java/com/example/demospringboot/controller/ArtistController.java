package com.example.demospringboot.controller;

import com.example.demospringboot.entity.Artist;
import com.example.demospringboot.service.ArtistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Artist Management", description = "Operations related to artists")
@RequestMapping("/api/artists")
public class ArtistController {

    // @Autowired
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(summary = "Get all artists", description = "Returns a paginated list of all artists")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public ResponseEntity<Page<Artist>> getAllArtists(Pageable pageable) {
        return ResponseEntity.ok(artistService.getAllArtists(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        return ResponseEntity.ok(artistService.createArtist(artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        return ResponseEntity.ok(artistService.updateArtist(id, artistDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Artist> getArtistByEmail(@PathVariable String email) {
        return ResponseEntity.ok(artistService.getArtistByEmail(email));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Artist>> findArtistsByName(@RequestParam String name) {
        return ResponseEntity.ok(artistService.findArtistsByName(name));
    }
}