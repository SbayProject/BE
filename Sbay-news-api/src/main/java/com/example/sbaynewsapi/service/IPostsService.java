package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostsService {
    Page<Posts> getAll(String name,String title, Pageable pageable);

    Page<Posts> getAllUser(String type, String title, Pageable pageable);

    Page<Posts> getAllByEditor(Editors editors,String type, String title, Pageable pageable);

    Posts getDetailPost(Integer idPost);
}
