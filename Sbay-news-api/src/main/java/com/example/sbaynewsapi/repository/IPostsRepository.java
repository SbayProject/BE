package com.example.sbaynewsapi.repository;

import com.example.sbaynewsapi.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostsRepository extends JpaRepository<Posts,Integer> {
    @Query(value = "select p.id,p.title,p.content ,p.image,p.is_public,p.is_delete ,p.create_date,p.update_date,p.editor_id,p.type_post_id from posts as p inner join type_post as t on t.id = p.type_post_id " +
            "where t.name like concat('%',:name,'%') and p.title like concat('%',:title,'%') and  p.is_delete =false and p.is_public =true",nativeQuery = true)
    Page<Posts> getAll(@Param("name") String s,@Param("title") String title, Pageable pageable);

    @Query(value = "select p.id,p.title,p.content ,p.image,p.is_public,p.is_delete ,p.create_date,p.update_date,p.editor_id,p.type_post_id from posts as p inner join type_post as t on t.id = p.type_post_id " +
            "where t.name like concat('%',:name,'%') and p.title like concat('%',:title,'%') ",nativeQuery = true)
    Page<Posts> getAllUser(@Param("name") String name,@Param("title") String title, Pageable pageable);

    @Query(value = "select p.id,p.title,p.content ,p.image,p.is_public,p.is_delete ,p.create_date,p.update_date,p.editor_id,p.type_post_id from posts as p inner join type_post as t on t.id = p.type_post_id " +
            "where t.name like concat('%',:name,'%') and p.title like concat('%',:title,'%') and p.editor_id=:id",nativeQuery = true)
    Page<Posts> getAllByEditor(@Param("id") Integer id,@Param("name") String name,@Param("title") String title, Pageable pageable);
    @Query(value = "select p.id,p.title,p.content ,p.image,p.is_public,p.is_delete ,p.create_date,p.update_date,p.editor_id,p.type_post_id from posts as p inner join type_post as t on t.id = p.type_post_id " +
            "order by p.create_date desc limit 4",nativeQuery = true)
    List<Posts> getNewPost();
}
