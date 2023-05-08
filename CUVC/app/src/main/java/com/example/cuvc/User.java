package com.example.cuvc;

public class User {
    public String id;
    public String name;
    public String email;
    private String password;
    boolean isadmin ;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String email, String password,boolean isadmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isadmin=isadmin ;
    }



    public String getPassword() {
        return password;
    }


}