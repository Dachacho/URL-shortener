package com.example.url_shortener.repositories;

import com.example.url_shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, String> {
    Optional<Url> findByOriginalUrl(String originalUrl);

    @Modifying
    @Query("UPDATE Url u SET u.visitCount = u.visitCount + 1 WHERE u.id = :id")
    void incrementVisitCount(String id);
}
