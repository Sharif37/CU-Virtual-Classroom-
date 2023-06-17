package com.example.cuvc;

public class User {
    public String id;
    public String name;
    public String email;
    private String password;
    public  boolean admin;
    private String fcmToken;

    public User() {

    }

    public User(String id, String name, String email, String password, boolean admin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }




    public String getPassword() {
        return password;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }


}