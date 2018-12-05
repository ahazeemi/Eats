package com.example.lenovo.eats.ClassModel;

import java.io.Serializable;

public class Bill_Feedback implements Serializable {
    private String name, phone, comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    private int rating;

    public Bill_Feedback()
    {
    }

    public Bill_Feedback(String name, String phone, String comments, int rating) {
        this.name = name;
        this.phone = phone;
        this.comments = comments;
        this.rating = rating;
    }
}
