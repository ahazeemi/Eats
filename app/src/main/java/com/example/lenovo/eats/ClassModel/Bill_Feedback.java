package com.example.lenovo.eats.ClassModel;

public class Bill_Feedback {
    private String name, phone, comments;
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
