package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.repository.IEditorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EditorsService implements IEditorsService{
    @Autowired
    private IEditorsRepository iEditorsRepository;
    @Override
    public Page<Editors> getAll(String name, Pageable pageable) {
        if (name.equals("null")){
            return iEditorsRepository.getAll("",pageable);

        }else {
            return iEditorsRepository.getAll(name,pageable);

        }
    }
}
