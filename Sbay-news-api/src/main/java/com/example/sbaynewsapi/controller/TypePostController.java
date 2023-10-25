package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.dto.TypePostDto;
import com.example.sbaynewsapi.model.TypePost;
import com.example.sbaynewsapi.service.ITypePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/typePost")
public class TypePostController {
    @Autowired
    private ITypePostService iTypePostService;

    @GetMapping("")
    public ResponseEntity<List<TypePost>> getPosts(@RequestParam(defaultValue = "") String name){
        try{
            return new ResponseEntity<>(iTypePostService.getAll(name), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createTypePost")
    public ResponseEntity<?> createTypePost(@RequestBody @Valid TypePostDto typePostDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return iTypePostService.createTypePost(typePostDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/deleteTypePost")
    public ResponseEntity<?> deleteTypePost(@RequestBody TypePostDto typePostDto) {
        try {
            return iTypePostService.deleteTypePost(typePostDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("updateTypePost")
    public ResponseEntity<?> updateTypePost(@RequestBody TypePostDto typePostDto) {
        try {
            return iTypePostService.updateTypePost(typePostDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
