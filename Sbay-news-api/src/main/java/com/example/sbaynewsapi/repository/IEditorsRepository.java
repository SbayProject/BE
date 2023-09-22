package com.example.sbaynewsapi.repository;

import com.example.sbaynewsapi.model.Editors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEditorsRepository extends JpaRepository<Editors,Integer> {
}
