package com.example.urlshortener.controller;


import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ViewController {

    private final UrlRepository repository;
    private final UrlService service;

    public ViewController(UrlRepository repository, UrlService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<UrlMapping> urls = repository.findAll();
        model.addAttribute("urls", urls);
        return "index";
    }

    @PostMapping("/shorten")
    public String shorten(@RequestParam String originalUrl) {
        service.createShortUrl(originalUrl);
        return "redirect:/";
    }
}