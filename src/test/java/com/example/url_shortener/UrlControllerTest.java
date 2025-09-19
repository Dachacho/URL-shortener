package com.example.url_shortener;

import com.example.url_shortener.controllers.UrlController;
import com.example.url_shortener.dtos.UrlResponse;
import com.example.url_shortener.exception.GlobalErrorHandler;
import com.example.url_shortener.models.Url;
import com.example.url_shortener.services.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UrlControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlService urlService;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(urlController)
                .setControllerAdvice(new GlobalErrorHandler()).build();
    }

    @Test
    void testCreateShortUrl_success() throws Exception {
        when(urlService.createShortUrl(any())).thenReturn("abc123");

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\": \"https://google.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("http://localhost:8080/abc123"));
    }

    @Test
    void createShortUrl_returns400OnInvalidUrl() throws Exception {
        when(urlService.createShortUrl("not_a_url")).thenThrow(new IllegalArgumentException("Invalid URL format"));

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\": \"not_a_url\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUrlById_redirectsIfFound() throws Exception {
        Url url = new Url("abc123", "https://google.com");
        when(urlService.resolveAndIncrement("abc123")).thenReturn(url);

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));
    }

    @Test
    void getUrlById_returns404IfNotFound() throws Exception {
        when(urlService.resolveAndIncrement("notfound")).thenReturn(null);

        mockMvc.perform(get("/notfound"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUrlInfo_returns200IfFound() throws Exception {
        UrlResponse resp = new UrlResponse("abc123", "https://google.com", false, null, 5L);
        when(urlService.getUrlInfo("abc123")).thenReturn(resp);

        mockMvc.perform(get("/abc123/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("abc123"))
                .andExpect(jsonPath("$.originalUrl").value("https://google.com"));
    }

    @Test
    void getUrlInfo_returns404IfNotFound() throws Exception {
        when(urlService.getUrlInfo("notfound")).thenReturn(null);

        mockMvc.perform(get("/notfound/info"))
                .andExpect(status().isNotFound());
    }


}
