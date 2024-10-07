package com.schemax.foodforward.controller;

import com.schemax.foodforward.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    TestService testService;


    @PostMapping("/test")
    public ResponseEntity<String> testApi(@RequestBody String req) {
        return testService.test();
    }

}
