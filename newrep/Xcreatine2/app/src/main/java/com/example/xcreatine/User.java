package com.example.xcreatine;

public class User {

    private String id;
    private String name;
    private String email;
    private String contact;
    private String plan;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String email, String contact, String plan) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.plan = plan;
    }

    public String getName() {
        return name;
    }

    // Getters and setters
    // ...
}
