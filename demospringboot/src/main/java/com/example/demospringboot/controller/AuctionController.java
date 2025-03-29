package com.example.demospringboot.controller;

import com.example.demospringboot.entity.Auction;
import com.example.demospringboot.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

        @Autowired
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    public ResponseEntity<List<Auction>> getAllAuctions() {
        return ResponseEntity.ok(auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        return ResponseEntity.ok(auctionService.getAuctionById(id));
    }

    @PostMapping
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction, 
                                                @RequestParam Long artworkId) {
        return ResponseEntity.ok(auctionService.createAuction(auction, artworkId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody Auction auctionDetails) {
        return ResponseEntity.ok(auctionService.updateAuction(id, auctionDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/artwork/{artworkId}")
    public ResponseEntity<List<Auction>> getAuctionsByArtwork(@PathVariable Long artworkId) {
        return ResponseEntity.ok(auctionService.getAuctionsByArtwork(artworkId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Auction>> getActiveAuctions() {
        return ResponseEntity.ok(auctionService.getActiveAuctions());
    }

    @PostMapping("/{id}/bid")
    public ResponseEntity<Auction> placeBid(@PathVariable Long id, @RequestParam double bidAmount) {
        return ResponseEntity.ok(auctionService.placeBid(id, bidAmount));
    }
}