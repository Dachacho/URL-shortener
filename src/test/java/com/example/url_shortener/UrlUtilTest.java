package com.example.url_shortener;

import com.example.url_shortener.utils.UrlUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UrlUtilTest {
    @Test
    void testGenerateShortId(){
        for (int i = 0; i < 1000; i++) {
            String id = UrlUtils.generateShortId();
            assertEquals(6, id.length(), "Short ID should be 6 characters long");
            assertTrue(id.matches("^[a-zA-Z0-9]{6}$"), "Short ID should only contain a-z, A-Z, 0-9");
        }
    }

    @Test
    void testNormalizeUrl(){
        assertEquals("https://example.com", UrlUtils.normalizeUrl("example.com"));
        assertEquals("http://example.com", UrlUtils.normalizeUrl("http://example.com"));
        assertEquals("https://example.com", UrlUtils.normalizeUrl("https://example.com"));
        assertEquals("https://example.com/path", UrlUtils.normalizeUrl("example.com/path"));
        assertEquals("https://example.com", UrlUtils.normalizeUrl("   example.com   "));
        assertEquals("https://example.com", UrlUtils.normalizeUrl("\"example.com\""));
        assertEquals("", UrlUtils.normalizeUrl(null));
    }

    @Test
    void testIsValidUrl(){
        assertTrue(UrlUtils.isValidUrl("https://example.com"));
        assertTrue(UrlUtils.isValidUrl("http://example.com"));
        assertTrue(UrlUtils.isValidUrl("https://example.com/path?query=1"));
        assertTrue(UrlUtils.isValidUrl("http://localhost:8080"));

        assertFalse(UrlUtils.isValidUrl("ftp://example.com"));
        assertFalse(UrlUtils.isValidUrl("example.com"));
        assertFalse(UrlUtils.isValidUrl("htp://example.com"));
        assertFalse(UrlUtils.isValidUrl("http//example.com"));
        assertFalse(UrlUtils.isValidUrl("just some text"));
        assertFalse(UrlUtils.isValidUrl(""));
        assertFalse(UrlUtils.isValidUrl(null));
    }
}
