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
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    private String id;
    @Column(nullable = false)
    private String originalUrl;
    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant expiresAt;

    private boolean disabled = false;

    private long visitCount = 0;
}
