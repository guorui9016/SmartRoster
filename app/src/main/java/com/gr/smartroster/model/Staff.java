package com.gr.smartroster.model;

public class Staff {
    private String email;
    private String groupName;
    private String contractType;
    private String company;
    private String admin;

    public Staff() {
    }

    public Staff(String email, String groupName, String contractType, String company, String admin) {
        this.email = email;
        this.groupName = groupName;
        this.contractType = contractType;
        this.company = company;
        this.admin = admin;
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

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
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
