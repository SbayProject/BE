package com.example.sbaynewsapi.model;

import javax.persistence.*;

@Entity
@Table(name = "type_post")
public class TypePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(columnDefinition = "BIT DEFAULT 0", updatable = true)
    private boolean isDelete;

    public TypePost() {
    }

    public TypePost(Integer id, String name, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.isDelete = isDelete;
    }

    public TypePost(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
