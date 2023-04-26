package io.petra.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class AuthController {


    @GetMapping
    private String testPage() {
        return "auth";
    }


    @PostMapping("/auth/google")
    public ResponseEntity<?> AuthToAccessToken(String string) {
        //new OAuth2AccessToken()
        return ResponseEntity.ok(null);
    }
}
