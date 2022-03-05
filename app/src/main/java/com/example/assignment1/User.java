package com.example.assignment1;

import android.widget.EditText;

public class User {
    public String myEmail, myPass;

    public User(EditText myEmail, EditText myPass){
    }

    public User(String myEmail, String myPass){
        this.myEmail = myEmail;
        this.myPass = myPass;
    }

}


