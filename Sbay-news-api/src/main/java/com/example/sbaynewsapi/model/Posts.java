package com.example.sbaynewsapi.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 50)
    private String title;
    @Column(nullable = false)
    private String content;
    private String image;
    @Column(columnDefinition = "BIT DEFAULT 0", updatable = true)
    private boolean isPublic;
    @Column(columnDefinition = "BIT DEFAULT 0", updatable = true)
    private boolean isDelete;
    @Column(name = "create_date", columnDefinition = "DATETIME DEFAULT now()", updatable = false)
    private LocalDateTime createDate;
    @Column(name = "update_date", columnDefinition = "DATETIME DEFAULT now()", updatable = true)
    private LocalDateTime updateDate;
    @OneToOne
    @JoinColumn(name = "editor_id")
    private Editors editors;
    @OneToOne
    @JoinColumn(name = "type_post_id")
    private TypePost typePost;

    public Posts() {
    }

    public Posts(Integer id, String title, String content, String image, boolean isPublic, boolean isDelete, LocalDateTime createDate, LocalDateTime updateDate, Editors editors, TypePost typePost) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.isPublic = isPublic;
        this.isDelete = isDelete;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.editors = editors;
        this.typePost = typePost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Editors getEditors() {
        return editors;
    }

    public void setEditors(Editors editors) {
        this.editors = editors;
    }

    public TypePost getTypePost() {
        return typePost;
    }

    public void setTypePost(TypePost typePost) {
        this.typePost = typePost;
    }
}
