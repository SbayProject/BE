package com.example.sbaynewsapi.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @Column(name = "verification_code")
    private Integer verificationCode;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;
    @Column(name = "email",length = 50)
    private String email;

    public Users() {
    }

    public Users(Integer id, String username, String password, Integer verificationCode, Roles roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.verificationCode = verificationCode;
        this.roles = roles;
    }

    public Users(Integer id, String username, String password, Integer verificationCode, Roles roles, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.verificationCode = verificationCode;
        this.roles = roles;
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
