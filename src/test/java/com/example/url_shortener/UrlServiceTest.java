package com.example.url_shortener;

import com.example.url_shortener.dtos.UrlResponse;
import com.example.url_shortener.models.Url;
import com.example.url_shortener.repositories.UrlRepository;
import com.example.url_shortener.services.UrlService;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlServiceTest {
    @Test
    void createShortUrl_throwsOnInvalidUrl() {
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);
        assertThrows(IllegalArgumentException.class, () -> service.createShortUrl("not a url"));
    }

    @Test
    void createShortUrl_returnsExistingIdIfPresent() {
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);
        Url existing = new Url("abc123", "https://google.com");
        when(repo.findByOriginalUrl("https://google.com")).thenReturn(Optional.of(existing));
        String id = service.createShortUrl("google.com");
        assertEquals("abc123", id);
    }

    @Test
    void resolveAndIncrement_incrementsIfActive() {
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);
        Url url = new Url("abc123", "https://google.com");
        url.setVisitCount(0L);
        when(repo.findById("abc123")).thenReturn(Optional.of(url));

        Url result = service.resolveAndIncrement("abc123");
        verify(repo).incrementVisitCount("abc123");
        assertNotNull(result);
        assertEquals(1L, result.getVisitCount());
    }

    @Test
    void resolveAndIncrement_returnsNullIfExpired() {
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);
        Url url = new Url("abc123", "https://google.com");
        url.setExpiresAt(Instant.now().minusSeconds(60));
        when(repo.findById("abc123")).thenReturn(Optional.of(url));
        Url result = service.resolveAndIncrement("abc123");
        assertNull(result);
    }

    @Test
    void getUrlInfo_returnsResponse_whenFound() {
        // Arrange
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);

        Instant expiresAt = Instant.now().plusSeconds(3600);
        Url entity = new Url("abc123", "https://example.com");
        entity.setDisabled(false);
        entity.setExpiresAt(expiresAt);
        entity.setVisitCount(42L);

        when(repo.findById("abc123")).thenReturn(Optional.of(entity));

        UrlResponse resp = service.getUrlInfo("abc123");

        assertNotNull(resp);
        assertEquals("abc123", resp.getId());
        assertEquals("https://example.com", resp.getOriginalUrl());
        assertFalse(resp.isDisabled());
        assertEquals(42L, resp.getVisitCount());
        assertNotNull(resp.getExpiresAt());
        assertEquals(expiresAt, resp.getExpiresAt());
    }

    @Test
    void getUrlInfo_returnsNull_whenNotFound() {
        UrlRepository repo = mock(UrlRepository.class);
        UrlService service = new UrlService(repo);

        when(repo.findById("missing")).thenReturn(Optional.empty());

        UrlResponse resp = service.getUrlInfo("missing");

        assertNull(resp);
    }
}
