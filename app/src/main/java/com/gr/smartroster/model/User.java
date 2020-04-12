package com.gr.smartroster.model;

public class User {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String password) {
        this.password = password;
    }

    public User() {
    }
}
