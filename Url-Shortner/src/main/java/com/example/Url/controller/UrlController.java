package com.example.Url.controller;



import com.example.Url.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    // POST request to create a short URL
    @PostMapping("/shorten")
    public String shorten(@RequestBody String originalUrl) {
        // In a real app, use a DTO (Data Transfer Object) instead of raw String
        String shortCode = service.shortenUrl(originalUrl);
        return "http://localhost:8080/api/" + shortCode;
    }

    // GET request to redirect
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = service.getOriginalUrl(shortCode);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Redirect
    }
}