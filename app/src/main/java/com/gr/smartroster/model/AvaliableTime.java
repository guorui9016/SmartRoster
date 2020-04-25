package com.gr.smartroster.model;

import com.google.firebase.Timestamp;

import java.util.Comparator;

public class AvaliableTime implements Comparable<AvaliableTime> {
    private String groupName;
    private String company;
    private String email;
    private Timestamp date;
    private Timestamp startTime;
    private Timestamp endTime;
    private String docID;

    public AvaliableTime() {
    }

    public AvaliableTime(String groupName, String company, String email, Timestamp date, Timestamp startTime, Timestamp endTime, String docID) {
        this.groupName = groupName;
        this.company = company;
        this.email = email;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.docID = docID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    @Override
    public int compareTo(AvaliableTime o) {
        return this.startTime.compareTo(o.startTime);
    }
}
