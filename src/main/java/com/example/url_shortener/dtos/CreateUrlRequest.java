package com.example.url_shortener.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUrlRequest {
    private String originalUrl;
}
