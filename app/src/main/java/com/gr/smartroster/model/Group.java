package com.gr.smartroster.model;

public class Group {
    private String company;
    private String groupName_Lower;
    private String company_Lower;
    private String manager;
    private String groupName;
    public Group() {
    }

    public Group(String company, String groupname_lower, String cmpany_lower, String manager, String groupName) {
        this.company = company;
        this.groupName_Lower = groupname_lower;
        this.company_Lower = cmpany_lower;
        this.manager = manager;
        this.groupName = groupName;
    }

    public String getCompany() {
        return company;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGroupname_lower() {
        return groupName_Lower;
    }

    public void setGroupname_lower(String groupname_lower) {
        this.groupName_Lower = groupname_lower;
    }

    public String getCmpany_lower() {
        return company_Lower;
    }

    public void setCmpany_lower(String cmpany_lower) {
        this.company_Lower = cmpany_lower;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
