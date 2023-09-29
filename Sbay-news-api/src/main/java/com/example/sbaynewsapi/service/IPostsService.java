package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.dto.PostsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPostsService {
    Page<Posts> getAll(String name,String title, Pageable pageable);

    Page<Posts> getAllUser(String type, String title, Pageable pageable);

    Page<Posts> getAllByEditor(Editors editors,String type, String title, Pageable pageable);

    Posts getDetailPost(Integer idPost);

    ResponseEntity<?> createPost(Posts posts);

    ResponseEntity<?> browsePost(Integer id);

    ResponseEntity<?> deletePost(Posts posts);

    List<Posts> getNewPost();

    ResponseEntity<?> updatePost(PostsDto postsDto, Posts posts);

    List<Posts> getPostByType(Integer id);
}
