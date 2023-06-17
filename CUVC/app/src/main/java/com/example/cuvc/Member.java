package com.example.cuvc;

public class Member {
    private String id;
    private String name;
    private boolean isAdmin;

    public Member() {
        // Required empty constructor for Firebase
    }

    public Member(String id, String name,boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin =isAdmin ;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
