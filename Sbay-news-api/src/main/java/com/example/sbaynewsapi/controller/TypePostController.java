package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.service.ITypePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/typePost")
public class TypePostController {
    @Autowired
    private ITypePostService iTypePostService;
}
