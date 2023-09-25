package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Editors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IEditorsService {
    Page<Editors> getAll(String name, Pageable pageable);

    Editors getEditor(String username);

    Editors getDetailEditor(Integer idEditor);

    ResponseEntity<?> createEditor(Editors editors);
}
