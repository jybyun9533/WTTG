package com.example.wttg.Model;

public class User {
    private String id;
    private String name;
    private String contact;
    private String birth;
    private String email;

    public User(String id, String name, String contact, String birth, String email) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.birth = birth;
        this.email = email;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
