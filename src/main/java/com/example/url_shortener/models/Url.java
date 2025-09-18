package com.example.url_shortener.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    @Id
    private String id;
    @Column(nullable = false)
    private String originalUrl;
    @Column(nullable = false)
    private Instant createdAt;

    private Instant expiresAt;

    private boolean disabled = false;

    private long visitCount;

    public Url(String id, String originalUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.createdAt = Instant.now();
        this.disabled = false;
        this.visitCount = 0L;
    }
}
