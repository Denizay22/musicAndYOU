package com.example.mymusicapp.models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private static int counter = 0;
    private final int userID;
    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String email;
    private final String password; //todo password change
    //todo add photo

    public UserModel(String name, String surname, String email, String phoneNumber, String password){
        this.userID = counter++;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
