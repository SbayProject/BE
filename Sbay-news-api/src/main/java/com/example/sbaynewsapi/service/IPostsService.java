package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostsService {
    Page<Posts> getAll(String name,String title, Pageable pageable);
}
