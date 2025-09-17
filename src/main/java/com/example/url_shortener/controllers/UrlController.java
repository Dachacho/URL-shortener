package com.example.url_shortener.controllers;

import com.example.url_shortener.models.Url;
import com.example.url_shortener.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Url> getUrlById(@PathVariable String id) {
        String url = urlService.findById(id);
        if (url != null) {
            return ResponseEntity.ok(new Url(id, url));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createShortUrl(@RequestBody Url url) {
        String shortId = urlService.createShortUrl(url.getOriginalUrl());
        return ResponseEntity.ok(shortId);
    }
}
