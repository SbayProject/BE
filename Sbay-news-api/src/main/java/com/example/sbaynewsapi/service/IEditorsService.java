package com.example.sbaynewsapi.service;

import com.example.sbaynewsapi.model.Editors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEditorsService {
    Page<Editors> getAll(String name, Pageable pageable);
}
