package com.example.sbaynewsapi.repository;

import com.example.sbaynewsapi.model.TypePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITypePostRepository extends JpaRepository<TypePost,Integer> {
    @Query(value = "SELECT * from type_post as tp where tp.name like concat('%',:nameSearch,'%') and is_delete=false",nativeQuery = true)
    List<TypePost> getAllByDeleteIsFalse(String nameSearch);
}
