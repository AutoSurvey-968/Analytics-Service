package com.revature.autosurvey.analytics.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("analyticserror")
public class ErrorController {

  @GetMapping
  public ResponseEntity<Flux<Object>> handleGet() {
    return ResponseEntity.ok(Flux.empty());
  }
  
}