package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.config.JwtUserDetails;
import com.example.sbaynewsapi.dto.PostsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.service.IEditorsService;
import com.example.sbaynewsapi.service.IPostsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Calendar;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostsController {
    @Autowired
    private IPostsService iPostsService;
    @Autowired
    private IEditorsService iEditorsService;
    // danh sách bài viết (tất cả)
    @GetMapping("")
    public ResponseEntity<Page<Posts>> getPosts(@RequestParam(value = "type",defaultValue = "null") String type,@RequestParam(value = "title",defaultValue = "null") String title, @RequestParam( value = "page",defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page,9);
        try{
            return new ResponseEntity<>(iPostsService.getAll(type,title,pageable), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // Quản lý danh sách bài viết (admin)
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<Page<Posts>> getPostsUser(@RequestParam(value = "type",defaultValue = "null") String type,@RequestParam(value = "title",defaultValue = "null") String title, @RequestParam( value = "page",defaultValue = "0") Integer page){
        Pageable pageable = PageRequest.of(page,9);
        try{
            return new ResponseEntity<>(iPostsService.getAllUser(type,title,pageable), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // Quản lý danh sách bài viết (editor)
//    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @GetMapping("/editor")
    public ResponseEntity<Page<Posts>> getPostsEditor(@RequestParam(value = "type",defaultValue = "null") String type,@RequestParam(value = "title",defaultValue = "null") String title, @RequestParam( value = "page",defaultValue = "0") Integer page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
        Editors editors = iEditorsService.getEditor(principal.getUsername());
        Pageable pageable = PageRequest.of(page,9);
        try{
            return new ResponseEntity<>(iPostsService.getAllByEditor(editors,type,title,pageable), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // chi tiết bài viết (tất cả)
    @GetMapping("/detail/{idPost}")
    public ResponseEntity<Posts> getDetailPost(@PathVariable("idPost") Integer idPost){
        try{
            return new ResponseEntity<>(iPostsService.getDetailPost(idPost), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // them moi bai viet
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostsDto postsDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Editors editors = iEditorsService.getEditor(principal.getUsername());
            Posts posts =new Posts();
            BeanUtils.copyProperties(postsDto,posts);
            LocalDateTime currentDateTime = LocalDateTime.now();
            posts.setCreateDate(currentDateTime);
            posts.setTypePost(postsDto.getTypePost());
            posts.setEditors(editors);
            return iPostsService.createPost(posts);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
