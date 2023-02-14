package com.example.testing;

import java.io.Serializable;

public class Account implements Serializable {
    public String EMAIL;
    public String NAME;
    public String PASSWORD;
    public double MONEY;
    public Account(String email, String name, String password, double money){
        this.EMAIL = email;
        this.NAME = name;
        this.PASSWORD = password;
        this.MONEY = money;
    }
}
