package com.utehy.timviec247.models;

public class Account {
    private String id,email,fullName;
    private boolean type;

    public Account() {
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Account(String id, String email, String fullName, boolean type) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
