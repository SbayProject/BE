package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.service.IPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostsController {
    @Autowired
    private IPostsService iPostsService;
    @GetMapping("")
    public ResponseEntity<Page<Posts>> getPosts(@RequestParam(value = "type",defaultValue = "null") String type,@RequestParam(value = "title",defaultValue = "null") String title, @RequestParam( value = "page",defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page,9);
        try{
            return new ResponseEntity<>(iPostsService.getAll(type,title,pageable), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
