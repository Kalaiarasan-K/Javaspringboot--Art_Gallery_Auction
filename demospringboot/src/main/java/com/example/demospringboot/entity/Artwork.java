package com.example.demospringboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artworks")
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    @JsonBackReference
    private Artist artist;

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Auction> auctions = new ArrayList<>();

    // Constructors
    public Artwork() {}

    public Artwork(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { 
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price; 
    }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public List<Auction> getAuctions() { return auctions; }
    public void setAuctions(List<Auction> auctions) { this.auctions = auctions; }

    // Helper methods for auctions
    public void addAuction(Auction auction) {
        auctions.add(auction);
        auction.setArtwork(this);
    }

    public void removeAuction(Auction auction) {
        auctions.remove(auction);
        auction.setArtwork(null);
    }
}