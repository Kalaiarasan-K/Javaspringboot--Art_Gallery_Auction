package com.example.demospringboot.service;

import com.example.demospringboot.entity.Artist;
import com.example.demospringboot.entity.Artwork;
import com.example.demospringboot.exception.ResourceNotFoundException;
import com.example.demospringboot.repository.ArtworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArtworkService {

    @Autowired
    private final ArtworkRepository artworkRepository;
    private final ArtistService artistService;

    public ArtworkService(ArtworkRepository artworkRepository, ArtistService artistService) {
        this.artworkRepository = artworkRepository;
        this.artistService = artistService;
    }

    public Page<Artwork> getAllArtworks(Pageable pageable) {
        return artworkRepository.findAll(pageable);
    }

    public Artwork getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork not found with id: " + id));
    }

    public Artwork createArtwork(Artwork artwork, Long artistId) {
        Artist artist = artistService.getArtistById(artistId);
        artwork.setArtist(artist);
        return artworkRepository.save(artwork);
    }

    public Artwork updateArtwork(Long id, Artwork artworkDetails) {
        Artwork artwork = getArtworkById(id);
        artwork.setTitle(artworkDetails.getTitle());
        artwork.setDescription(artworkDetails.getDescription());
        artwork.setPrice(artworkDetails.getPrice());
        return artworkRepository.save(artwork);
    }

    public void deleteArtwork(Long id) {
        Artwork artwork = getArtworkById(id);
        artworkRepository.delete(artwork);
    }

    public List<Artwork> getArtworksByArtist(Long artistId) {
        artistService.getArtistById(artistId); // Validate artist exists
        return artworkRepository.findByArtistId(artistId);
    }

    public List<Artwork> getArtworksByPriceRange(double minPrice, double maxPrice) {
        return artworkRepository.findByPriceRange(minPrice, maxPrice);
    }
}