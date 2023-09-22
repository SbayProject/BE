package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.repository.IPostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
