package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.dto.TypePostDto;
import com.example.sbaynewsapi.model.TypePost;
import com.example.sbaynewsapi.repository.ITypePostRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePostService implements ITypePostService {
    @Autowired
    private ITypePostRepository iTypePostRepository;

    @Override
    public List<TypePost> getAll(String name) {
        return iTypePostRepository.getAllByDeleteIsFalse(name);
    }

    @Override
    public ResponseEntity<?> createTypePost(TypePostDto typePostDto) {
        try {
            TypePost typePost = new TypePost();
            BeanUtils.copyProperties(typePostDto, typePost);
            iTypePostRepository.save(typePost);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteTypePost(TypePostDto typePostDto) {
        try {
            TypePost typePost = iTypePostRepository.findById(typePostDto.getId()).get();
            typePost.setDelete(true);
            iTypePostRepository.save(typePost);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateTypePost(TypePostDto typePostDto) {
        try {
            TypePost typePost = new TypePost();
            BeanUtils.copyProperties(typePostDto, typePost);
            typePost.setDelete(false);
            iTypePostRepository.save(typePost);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
