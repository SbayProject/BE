package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.dto.EditorsDto;
import com.example.sbaynewsapi.model.Editors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IEditorsService {
    Page<Editors> getAll(String name, Pageable pageable);

    Editors getEditor(String username);

    Editors getDetailEditor(Integer idEditor);

    ResponseEntity<?> createEditor(Editors editors);

    ResponseEntity<?> deleteEditor(Integer id);

    ResponseEntity<?> updateEditor(EditorsDto editorsDto);
}
