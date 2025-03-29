package com.example.demospringboot.exception;



public class InvalidAuctionException extends RuntimeException {
    public InvalidAuctionException(String message) {
        super(message);
    }
}