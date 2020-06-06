package com.gr.smartroster.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Staff {
    private String email, groupName, contractType, company, name, level;
    private boolean admin;
    private ArrayList<String> roles;


    public Staff() {
    }

    public Staff(String email, String groupName, String company, String name, boolean admin) {
        this.email = email;
        this.groupName = groupName;
        this.company = company;
        this.name = name;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
