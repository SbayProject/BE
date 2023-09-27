package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.repository.IPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostsService{
    @Autowired
    private IPostsRepository iPostsRepository;
    @Override
    public Page<Posts> getAll(String name,String title, Pageable pageable) {
        if (name.equals("null")){
            if (title.equals("null")){
                return iPostsRepository.getAll("","",pageable);
            }else {
                return iPostsRepository.getAll("",title,pageable);
            }
        }else {
            if (title.equals("null")){
                return iPostsRepository.getAll(name,"",pageable);
            }else {
                return iPostsRepository.getAll(name,title,pageable);
            }
        }
    }

    @Override
    public Page<Posts> getAllUser(String type, String title, Pageable pageable) {
        if (type.equals("null")){
            if (title.equals("null")){
                return iPostsRepository.getAllUser("","",pageable);
            }else {
                return iPostsRepository.getAllUser("",title,pageable);
            }
        }else {
            if (title.equals("null")){
                return iPostsRepository.getAllUser(type,"",pageable);
            }else {
                return iPostsRepository.getAllUser(type,title,pageable);
            }
        }
    }

    @Override
    public Page<Posts> getAllByEditor(Editors editors,String type, String title, Pageable pageable) {
        if (type.equals("null")){
            if (title.equals("null")){
                return iPostsRepository.getAllByEditor(editors.getId(),"","",pageable);
            }else {
                return iPostsRepository.getAllByEditor(editors.getId(),"",title,pageable);
            }
        }else {
            if (title.equals("null")){
                return iPostsRepository.getAllByEditor(editors.getId(),type,"",pageable);
            }else {
                return iPostsRepository.getAllByEditor(editors.getId(),type,title,pageable);
            }
        }
    }

    @Override
    public Posts getDetailPost(Integer idPost) {
        return iPostsRepository.findById(idPost).get();
    }

    @Override
    public ResponseEntity<?> createPost(Posts posts) {
        try {
            iPostsRepository.save(posts);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> browsePost(Integer id) {
        try {
            Posts posts =iPostsRepository.findById(id).get();
            if (posts !=null){
                posts.setPublic(true);
                iPostsRepository.save(posts);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deletePost(Posts posts) {
        try {
            posts.setDelete(true);
            iPostsRepository.save(posts);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Posts> getNewPost() {
        return iPostsRepository.getNewPost();
    }
}
