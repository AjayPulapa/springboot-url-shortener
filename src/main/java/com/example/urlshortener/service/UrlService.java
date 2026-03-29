
package com.example.urlshortener.service;

import com.example.urlshortener.dto.UrlResponse;
import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final SecureRandom random = new SecureRandom();

    private static final String BASE_URL = "http://localhost:8080/api/u/";
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public UrlResponse createShortUrl(String originalUrl) {

        // ✅ Step 1: Check if URL already exists
        Optional<UrlMapping> existing = repository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {
            UrlMapping mapping = existing.get();

            UrlResponse response = new UrlResponse();
            response.setCode(mapping.getShortCode());
            response.setOriginalUrl(mapping.getOriginalUrl());
            response.setShortUrl(BASE_URL + mapping.getShortCode());
            response.setCreatedAt(mapping.getCreatedAt());

            return response; // ✅ return existing instead of creating new
        }

        // ✅ Step 2: Create new if not exists
        String code = generateCode();

        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(code);
        mapping.setCreatedAt(LocalDateTime.now());

        try {
            repository.save(mapping);
        } catch (DataIntegrityViolationException e) {
            return createShortUrl(originalUrl); // retry
        }
        UrlResponse response = new UrlResponse();
        response.setCode(code);
        response.setOriginalUrl(originalUrl);
        response.setShortUrl(BASE_URL + code);
        response.setCreatedAt(mapping.getCreatedAt());

        return response;
    }

    public String getOriginalUrl(String code) {
        System.out.println(code);
        return repository.findByShortCode(code)
                .orElseThrow(() -> new RuntimeException("URL not found"))
                .getOriginalUrl();
    }

    // ✅ ensures uniqueness before insert
    private String generateUniqueCode() {
        String code;
        do {
            code = generateCode();
        } while (repository.existsByShortCode(code));
        return code;
    }

    private String generateCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    private String generateCode1() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
