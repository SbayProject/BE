package com.example.sbaynewsapi.repository;

import com.example.sbaynewsapi.model.Editors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEditorsRepository extends JpaRepository<Editors,Integer> {
    @Query(value = "select e.id,e.name,e.birthday ,e.gender,e.phone_number,e.email,e.address,e.image,e.is_delete ,e.create_date,e.update_date,e.user_id from editors as e  " +
            "where e.name like concat('%',:name,'%')  and  e.is_delete =false",nativeQuery = true)
    Page<Editors> getAll(String name, Pageable pageable);
}
