package com.example.url_shortener.services;

import com.example.url_shortener.dtos.UrlResponse;
import com.example.url_shortener.models.Url;
import com.example.url_shortener.repositories.UrlRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.example.url_shortener.utils.UrlUtils.*;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public Url resolveAndIncrement(String id) {
        Url url = urlRepository.findById(id).orElse(null);
        if (url != null && !url.isDisabled() && (url.getExpiresAt() == null || url.getExpiresAt().isAfter(Instant.now()))) {
            urlRepository.incrementVisitCount(id);
            url.setVisitCount(url.getVisitCount() + 1);
            return url;
        }
        return null;
    }

    public String createShortUrl(String originalUrl) {
        String normalizedUrl = normalizeUrl(originalUrl);
        if (!isValidUrl(normalizedUrl)) {
            throw new IllegalArgumentException("Invalid URL format");
        }
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

    public UrlResponse getUrlInfo(String id) {
        Url url =  urlRepository.findById(id).orElse(null);

        if (url == null) {
            return null;
        }

        return new UrlResponse(
            url.getId(),
            url.getOriginalUrl(),
            url.isDisabled(),
            url.getExpiresAt(),
            url.getVisitCount()
        );
    }
}
