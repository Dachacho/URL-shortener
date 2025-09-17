package com.example.url_shortener.services;

import com.example.url_shortener.models.Url;
import com.example.url_shortener.repositories.UrlRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String generateShortId() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortId = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortId.append(characters.charAt(index));
        }
        return shortId.toString();
    }

    public String createShortUrl(String originalUrl) {
        String existingId = urlRepository.findByOriginalUrl(originalUrl)
                .map(Url::getId)
                .orElse(null);
        if (existingId != null) {
            return existingId;
        }

        //crazy collision check lowk
        String shortId;
        do {
            shortId = generateShortId();
        } while (urlRepository.existsById(shortId));

        Url url = new Url(shortId, originalUrl);
        urlRepository.save(url);
        return shortId;
    }

    public String findById(String id) {
        return urlRepository.findById(id)
                .map(Url::getOriginalUrl)
                .orElse(null);
    }
}
