package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.dto.EditorsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.repository.IEditorsRepository;
import com.example.sbaynewsapi.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EditorsService implements IEditorsService {
    @Autowired
    private IEditorsRepository iEditorsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Page<Editors> getAll(String name, Pageable pageable) {
        if (name.equals("null")) {
            return iEditorsRepository.getAll("", pageable);

        } else {
            return iEditorsRepository.getAll(name, pageable);

        }
    }

    @Override
    public Editors getEditor(String username) {
        return iEditorsRepository.findByUsers_Username(username);
    }

    @Override
    public Editors getDetailEditor(Integer idEditor) {
        return iEditorsRepository.findById(idEditor).get();
    }

    @Transactional
    @Override
    public ResponseEntity<?> createEditor(Editors editors) {
        try {
            String password = passwordEncoder.encode(editors.getUsers().getPassword());
            editors.getUsers().setPassword(password);
            iUserRepository.save(editors.getUsers());
            iEditorsRepository.save(editors);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteEditor(Integer id) {
        try {
            Editors editors = iEditorsRepository.findById(id).get();
            editors.setDelete(true);
            iEditorsRepository.save(editors);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }


    }

    @Override
    public ResponseEntity<?> updateEditor(EditorsDto editorsDto) {
        try {
            Editors editors =iEditorsRepository.findById(editorsDto.getId()).get();
            editors.setAddress(editorsDto.getAddress());
            editors.setName(editorsDto.getName());
            editors.setBirthday(editorsDto.getBirthday());
            editors.setGender(editorsDto.getGender());
            editors.setPhoneNumber(editorsDto.getPhoneNumber());
            editors.setEmail(editorsDto.getEmail());
            editors.setImage(editorsDto.getImage());
            iEditorsRepository.save(editors);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
