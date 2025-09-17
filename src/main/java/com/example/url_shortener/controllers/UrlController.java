package com.example.url_shortener.controllers;

import com.example.url_shortener.dtos.CreateUrlRequest;
import com.example.url_shortener.models.Url;
import com.example.url_shortener.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Url> getUrlById(@PathVariable String id) {
        String url = urlService.findById(id);
        if (url != null) {
            return ResponseEntity.status(302).location(URI.create(url)).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createShortUrl(@RequestBody CreateUrlRequest url) {
        String shortId = urlService.createShortUrl(url.getOriginalUrl());
        String shortUrl = "http://localhost:8080/" + shortId;
        return ResponseEntity.ok(shortUrl);
    }
}
