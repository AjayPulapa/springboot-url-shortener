
package com.example.urlshortener.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String shortUrl;
    private String code;
    private String originalUrl;
    private LocalDateTime createdAt;

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
