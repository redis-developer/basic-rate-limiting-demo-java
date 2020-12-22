package com.limit.redis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PingController {
    @GetMapping("/api/ping")
    public ResponseEntity<String> getSimple() {
        return ResponseEntity.ok("PONG");
    }

}
