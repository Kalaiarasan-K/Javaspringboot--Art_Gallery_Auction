package com.example.demospringboot.repository;

import com.example.demospringboot.entity.Auction;
import com.example.demospringboot.entity.Auction.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByArtworkId(Long artworkId);
    
    List<Auction> findByStatus(AuctionStatus status);
    
    @Query("SELECT a FROM Auction a WHERE a.endTime > :now AND a.startTime <= :now")
    List<Auction> findActiveAuctions(@Param("now") LocalDateTime now);
}