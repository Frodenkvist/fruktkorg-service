package com.fruktkorgservice.controller;

import com.common.util.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> ping() {
        return JSON.message(HttpStatus.OK, "Pong");
    }
}
