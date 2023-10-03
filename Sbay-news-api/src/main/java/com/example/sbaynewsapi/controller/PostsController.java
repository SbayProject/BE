package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.config.JwtUserDetails;
import com.example.sbaynewsapi.dto.PostsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.model.Users;
import com.example.sbaynewsapi.service.IEditorsService;
import com.example.sbaynewsapi.service.IPostsService;
import com.example.sbaynewsapi.service.IUsersService;
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
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostsController {
    @Autowired
    private IPostsService iPostsService;
    @Autowired
    private IEditorsService iEditorsService;
    @Autowired
    private IUsersService iUsersService;

    // danh sách bài viết (tất cả)
    @GetMapping("")
    public ResponseEntity<Page<Posts>> getPosts(@RequestParam(value = "type", defaultValue = "null") String type, @RequestParam(value = "title", defaultValue = "null") String title, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        try {
            return new ResponseEntity<>(iPostsService.getAll(type, title, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/newPost")
    public ResponseEntity<List<Posts>> getNewPost() {
        try {
            return new ResponseEntity<>(iPostsService.getNewPost(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/typePostSearch")
    public ResponseEntity<Page<Posts>> getListPostsByTypeSearch(@RequestParam(value = "id") Integer id, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        try {
            return new ResponseEntity<>(iPostsService.getListPostsByTypeSearch(id, title, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostByType")
    public ResponseEntity<List<Posts>> getPostByType(@RequestParam("id") Integer id) {
        try {
            return new ResponseEntity<>(iPostsService.getPostByType(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Quản lý danh sách bài viết (admin,EDITOR)
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    @GetMapping("/user")
    public ResponseEntity<Page<Posts>> getPostsUser(@RequestParam(value = "type", defaultValue = "null") String type, @RequestParam(value = "title", defaultValue = "null") String title, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Users users = iUsersService.findByUsername(principal.getUsername());
            if (users.getRoles().getRoleName().equals("ROLE_ADMIN")) {
                return new ResponseEntity<>(iPostsService.getAllUser(type, title, pageable), HttpStatus.OK);
            } else {
                Editors editors = iEditorsService.getEditor(principal.getUsername());
                return new ResponseEntity<>(iPostsService.getAllByEditor(editors, type, title, pageable), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // chi tiết bài viết (tất cả)
    @GetMapping("/detail/{idPost}")
    public ResponseEntity<Posts> getDetailPost(@PathVariable("idPost") Integer idPost) {
        try {
            return new ResponseEntity<>(iPostsService.getDetailPost(idPost), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // them moi bai viet
    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostsDto postsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Editors editors = iEditorsService.getEditor(principal.getUsername());
            Posts posts = new Posts();
            BeanUtils.copyProperties(postsDto, posts);
            LocalDateTime currentDateTime = LocalDateTime.now();
            posts.setCreateDate(currentDateTime);
            posts.setTypePost(postsDto.getTypePost());
            posts.setEditors(editors);
            return iPostsService.createPost(posts);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // duyet bai viet
    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/browse")
    public ResponseEntity<?> browsePost(@RequestBody Posts posts) {
        try {
            return iPostsService.browsePost(posts.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa post (admin , editor)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    @PatchMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestBody Posts posts) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Users users = iUsersService.findByUsername(principal.getUsername());
            Posts posts1 = iPostsService.getDetailPost(posts.getId());
            if (users.getRoles().getRoleName().equals("ROLE_ADMIN")) {
                return iPostsService.deletePost(posts1);
            } else {
                Editors editors = iEditorsService.getEditor(principal.getUsername());
                if (editors.getId() == posts1.getEditors().getId()) {
                    return iPostsService.deletePost(posts1);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // sua bai viet
    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')")
    @PostMapping("/updatePost")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostsDto postsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Users users = iUsersService.findByUsername(principal.getUsername());
            Posts posts = iPostsService.getDetailPost(postsDto.getId());
            if (users.getRoles().getRoleName().equals("ROLE_ADMIN")) {
                return iPostsService.updatePost(postsDto, posts);
            } else {
                Editors editors = iEditorsService.getEditor(principal.getUsername());
                if (editors.getId() == posts.getEditors().getId()) {
                    return iPostsService.updatePost(postsDto, posts);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
