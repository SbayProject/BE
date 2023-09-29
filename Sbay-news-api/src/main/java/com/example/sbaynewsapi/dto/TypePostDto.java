package com.example.sbaynewsapi.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

public class TypePostDto {
    private Integer id;
    @NotNull
    private String name;
    private boolean isDelete;

    public TypePostDto() {
    }

    public TypePostDto(Integer id, String name, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.isDelete = isDelete;
    }

    public TypePostDto(Integer id, String name) {
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
