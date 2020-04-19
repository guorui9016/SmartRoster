package com.gr.smartroster.model;

import com.google.firebase.Timestamp;

public class Roster {
    private String email;
    private String groupName;
    private Timestamp  date;
    private Timestamp  startTime;
    private Timestamp endTime;
    private String company;
    private String role;

    public Roster() {
    }

    public Roster(String email, String groupName, Timestamp date, Timestamp startTime, Timestamp endTime, String company, String role) {
        this.email = email;
        this.groupName = groupName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.company = company;
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
