package com.example.demospringboot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String bio;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Artwork> artworks = new ArrayList<>();

    // Validation patterns
    private static final String NAME_REGEX = "^[A-Z][a-z]+(?:[\\s'-][A-Z]?[a-z]+)*$";
    private static final String BIO_REGEX = "^[A-Za-z0-9,.\\s]{5,200}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    // Constructors, getters, and setters
    public Artist() {}

    public Artist(String name, String bio, String email) {
        this.name = name;
        this.bio = bio;
        this.email = email;
    }

    // Getters and setters with validation
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (isValidPattern(name, NAME_REGEX)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid name format");
        }
    }

    public String getBio() { return bio; }
    public void setBio(String bio) {
        if (isValidPattern(bio, BIO_REGEX)) {
            this.bio = bio;
        } else {
            throw new IllegalArgumentException("Bio must be 5-200 characters");
        }
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (isValidPattern(email, EMAIL_REGEX)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public List<Artwork> getArtworks() { return artworks; }
    public void setArtworks(List<Artwork> artworks) { this.artworks = artworks; }

    // Helper method for adding artwork
    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
        artwork.setArtist(this);
    }

    // Helper method for removing artwork
    public void removeArtwork(Artwork artwork) {
        artworks.remove(artwork);
        artwork.setArtist(null);
    }

    private boolean isValidPattern(String value, String regex) {
        if (value == null) return false;
        return Pattern.compile(regex).matcher(value).matches();
    }
}