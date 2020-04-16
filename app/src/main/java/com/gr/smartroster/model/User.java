package com.gr.smartroster.model;

public class User {
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;

    public User() {
    }

    public User(String email, String password, String fullName) {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = "";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


