package com.schemax.foodforward.service.impl;

import com.schemax.foodforward.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Hello World", HttpStatus.ACCEPTED);
    }
}
