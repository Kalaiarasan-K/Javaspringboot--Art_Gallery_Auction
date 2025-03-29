package com.example.demospringboot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private double startingPrice;

    private double currentBid;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    @JsonBackReference
    private Artwork artwork;

    public enum AuctionStatus {
        UPCOMING, ONGOING, COMPLETED
    }

    // Constructors
    public Auction() {}

    public Auction(LocalDateTime startTime, LocalDateTime endTime, 
                  double startingPrice, AuctionStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
        this.currentBid = startingPrice;
        this.status = status;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { 
        if (endTime != null && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        this.startTime = startTime; 
    }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { 
        if (startTime != null && endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.endTime = endTime; 
    }

    public double getStartingPrice() { return startingPrice; }
    public void setStartingPrice(double startingPrice) { 
        if (startingPrice < 0) {
            throw new IllegalArgumentException("Starting price cannot be negative");
        }
        this.startingPrice = startingPrice; 
    }

    public double getCurrentBid() { return currentBid; }
    public void setCurrentBid(double currentBid) { 
        if (currentBid < startingPrice) {
            throw new IllegalArgumentException("Current bid cannot be less than starting price");
        }
        this.currentBid = currentBid; 
    }

    public AuctionStatus getStatus() { return status; }
    public void setStatus(AuctionStatus status) { this.status = status; }

    public Artwork getArtwork() { return artwork; }
    public void setArtwork(Artwork artwork) { this.artwork = artwork; }
}