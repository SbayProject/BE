package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Roles;
import com.example.sbaynewsapi.repository.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService implements IRolesService{
    @Autowired
    private IRolesRepository iRolesRepository;
    @Override
    public Roles getRole() {
        return iRolesRepository.findById(2).get();
    }
}
