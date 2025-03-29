package com.example.demospringboot.service;

import com.example.demospringboot.entity.Artist;
import com.example.demospringboot.exception.DuplicateResourceException;
import com.example.demospringboot.exception.ResourceNotFoundException;
import com.example.demospringboot.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArtistService {
    
    @Autowired
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Page<Artist> getAllArtists(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
    }

    public Artist createArtist(Artist artist) {
        Optional<Artist> existingArtist = artistRepository.findByEmail(artist.getEmail());
        if (existingArtist.isPresent()) {
            throw new DuplicateResourceException("Artist with email " + artist.getEmail() + " already exists");
        }
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Long id, Artist artistDetails) {
        Artist artist = getArtistById(id);
        artist.setName(artistDetails.getName());
        artist.setBio(artistDetails.getBio());
        
        if (!artist.getEmail().equals(artistDetails.getEmail())) {
            Optional<Artist> existingArtist = artistRepository.findByEmail(artistDetails.getEmail());
            if (existingArtist.isPresent()) {
                throw new DuplicateResourceException("Email already in use");
            }
            artist.setEmail(artistDetails.getEmail());
        }
        
        return artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        Artist artist = getArtistById(id);
        artistRepository.delete(artist);
    }

    public Artist getArtistByEmail(String email) {
        return artistRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with email: " + email));
    }

    public List<Artist> findArtistsByName(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name);
    }
}