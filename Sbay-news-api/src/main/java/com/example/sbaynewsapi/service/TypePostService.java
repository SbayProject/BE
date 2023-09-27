package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.TypePost;
import com.example.sbaynewsapi.repository.ITypePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePostService implements ITypePostService{
    @Autowired
    private ITypePostRepository iTypePostRepository;
    @Override
    public List<TypePost> getAll() {
        return iTypePostRepository.getAllByDeleteIsFalse();
    }

}
