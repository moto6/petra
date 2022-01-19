package com.board.accounts.controller;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {


    @GetMapping
    private String testPage() {
        return "auth";
    }


    @PostMapping("/auth/google")
    public ResponseEntity<OAuth2AccessToken> AuthToAccessToken(String string) {
        return null;
    }
}
