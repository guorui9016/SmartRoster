package com.gr.smartroster.model;

import java.util.ArrayList;

public class Staff {
    private String email;
    private String groupName;
    private String contractType;
    private String company;
    private boolean admin;
    private ArrayList<String> roles;

    public Staff(String email, String groupName, String contractType, String company, boolean admin) {
        this.email = email;
        this.groupName = groupName;
        this.contractType = contractType;
        this.company = company;
        this.admin = admin;
        this.roles = roles;
    }

    public Staff() {
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

    @Override
    public String toString() {
        return "Staff{" +
                "email='" + email + '\'' +
                ", groupName='" + groupName + '\'' +
                ", contractType='" + contractType + '\'' +
                ", company='" + company + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
