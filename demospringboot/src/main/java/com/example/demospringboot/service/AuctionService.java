package com.example.demospringboot.service;

import com.example.demospringboot.entity.Auction;
import com.example.demospringboot.entity.Auction.AuctionStatus;
import com.example.demospringboot.entity.Artwork;
import com.example.demospringboot.exception.InvalidAuctionException;
import com.example.demospringboot.exception.ResourceNotFoundException;
import com.example.demospringboot.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuctionService {

    @Autowired
    private final AuctionRepository auctionRepository;
    private final ArtworkService artworkService;

    public AuctionService(AuctionRepository auctionRepository, ArtworkService artworkService) {
        this.auctionRepository = auctionRepository;
        this.artworkService = artworkService;
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuctionById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auction not found with id: " + id));
    }

    public Auction createAuction(Auction auction, Long artworkId) {
        Artwork artwork = artworkService.getArtworkById(artworkId);
        
        if (auction.getStartTime().isAfter(auction.getEndTime())) {
            throw new InvalidAuctionException("Start time must be before end time");
        }
        
        if (auction.getStartingPrice() <= 0) {
            throw new InvalidAuctionException("Starting price must be positive");
        }
        
        auction.setArtwork(artwork);
        auction.setCurrentBid(auction.getStartingPrice());
        auction.setStatus(AuctionStatus.UPCOMING);
        
        return auctionRepository.save(auction);
    }

    public Auction updateAuction(Long id, Auction auctionDetails) {
        Auction auction = getAuctionById(id);
        
        if (auction.getStatus() == AuctionStatus.COMPLETED) {
            throw new InvalidAuctionException("Cannot modify a completed auction");
        }
        
        auction.setStartTime(auctionDetails.getStartTime());
        auction.setEndTime(auctionDetails.getEndTime());
        auction.setStartingPrice(auctionDetails.getStartingPrice());
        
        // Update status based on current time
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(auction.getEndTime())) {
            auction.setStatus(AuctionStatus.COMPLETED);
        } else if (now.isAfter(auction.getStartTime())) {
            auction.setStatus(AuctionStatus.ONGOING);
        } else {
            auction.setStatus(AuctionStatus.UPCOMING);
        }
        
        return auctionRepository.save(auction);
    }

    public void deleteAuction(Long id) {
        Auction auction = getAuctionById(id);
        auctionRepository.delete(auction);
    }

    public List<Auction> getAuctionsByArtwork(Long artworkId) {
        artworkService.getArtworkById(artworkId); // Validate artwork exists
        return auctionRepository.findByArtworkId(artworkId);
    }

    public List<Auction> getActiveAuctions() {
        return auctionRepository.findActiveAuctions(LocalDateTime.now());
    }

    public List<Auction> getAuctionsByStatus(AuctionStatus status) {
        return auctionRepository.findByStatus(status);
    }

    public Auction placeBid(Long auctionId, double bidAmount) {
        Auction auction = getAuctionById(auctionId);
        
        if (auction.getStatus() != AuctionStatus.ONGOING) {
            throw new InvalidAuctionException("Bids can only be placed on ongoing auctions");
        }
        
        if (bidAmount <= auction.getCurrentBid()) {
            throw new InvalidAuctionException("Bid must be higher than current bid");
        }
        
        auction.setCurrentBid(bidAmount);
        return auctionRepository.save(auction);
    }
}