package com.example.url_shortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponse {
    private String id;
    private String originalUrl;
    private boolean disabled;
    private Instant expiresAt;
    private long visitCount;
}
