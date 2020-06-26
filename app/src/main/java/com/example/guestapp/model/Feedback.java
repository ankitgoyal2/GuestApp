package com.example.guestapp.model;

import org.parceler.Parcel;


@Parcel
public class Feedback{
    private Machine machine;
    private String name, email, number, reviews;
    private boolean working;
    private String date;
    private long feedbackId;

//    public Object clone() throws CloneNotSupportedException
//    {
//        return super.clone();
//    }
    public Feedback()
    {

    }

    public Feedback(Machine machine, String name, String email, String number, String reviews, boolean working, String date, long feedbackId) {
        this.machine = machine;
        this.name = name;
        this.email = email;
        this.number = number;
        this.reviews = reviews;
        this.working = working;
        this.date = date;
        this.feedbackId = feedbackId;
    }

    public Machine getMachine() {
        return machine;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getReviews() {
        return reviews;
    }

    public boolean isWorking() {
        return working;
    }

    public String getDate() {
        return date;
    }

    public long getFeedbackId() {
        return feedbackId;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFeedbackId(long feedbackId) {
        this.feedbackId = feedbackId;
    }
}
