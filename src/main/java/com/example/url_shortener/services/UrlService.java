package com.example.url_shortener.services;

import com.example.url_shortener.models.Url;
import com.example.url_shortener.repositories.UrlRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String generateShortId() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortId = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            shortId.append(characters.charAt(index));
        }
        return shortId.toString();
    }

    private String normalizeUrl(String raw) {
        String url = raw == null ? "" : raw.trim();

        //for the quotes issue i had in the test cases
        if (url.startsWith("\"") && url.endsWith("\"") && url.length() > 1) {
            url = url.substring(1, url.length() - 1);
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }

        return url;
    }

    public String createShortUrl(String originalUrl) {
        String normalizedUrl = normalizeUrl(originalUrl);
        String existingId = urlRepository.findByOriginalUrl(normalizedUrl)
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

        Url url = new Url(shortId, normalizedUrl);
        urlRepository.save(url);
        return shortId;
    }

    public String findById(String id) {
        return urlRepository.findById(id)
                .map(Url::getOriginalUrl)
                .orElse(null);
    }
}
