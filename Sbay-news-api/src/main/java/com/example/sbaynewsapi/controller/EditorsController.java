package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.service.IEditorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/editor")
public class EditorsController {
    @Autowired
    private IEditorsService iEditorsService;
    @GetMapping("")
    public ResponseEntity<Page<Editors>> getEditor(@RequestParam(value = "name",defaultValue = "null") String name, @RequestParam( value = "page",defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page,9);
        try{
            return new ResponseEntity<>(iEditorsService.getAll(name,pageable), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
