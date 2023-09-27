package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.model.TypePost;
import com.example.sbaynewsapi.service.ITypePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/typePost")
public class TypePostController {
    @Autowired
    private ITypePostService iTypePostService;
    @GetMapping("")
    public ResponseEntity<List<TypePost>> getPosts( ){
        try{
            return new ResponseEntity<>(iTypePostService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
