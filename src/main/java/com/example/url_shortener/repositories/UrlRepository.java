package com.example.url_shortener.repositories;

import com.example.url_shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, String> {
    Optional<Url> findByOriginalUrl(String originalUrl);
}
