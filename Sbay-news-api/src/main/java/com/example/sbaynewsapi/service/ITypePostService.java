package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.dto.TypePostDto;
import com.example.sbaynewsapi.model.TypePost;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITypePostService {
    List<TypePost> getAll(String name);

    ResponseEntity<?> createTypePost(TypePostDto typePostDto);

    ResponseEntity<?> deleteTypePost(TypePostDto typePostDto);

    ResponseEntity<?> updateTypePost(TypePostDto typePostDto);
}
