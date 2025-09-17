package com.example.url_shortener;

import com.example.url_shortener.models.Url;
import com.example.url_shortener.repositories.UrlRepository;
import com.example.url_shortener.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class controllers {
    private final UrlService urlService;

    public controllers(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Url> getUrlById(@PathVariable String id) {
        String url = urlService.findById(id);
        if (url != null) {
            return ResponseEntity.ok(new Url(id, url));
        }
        return ResponseEntity.notFound().build();
    }

}
