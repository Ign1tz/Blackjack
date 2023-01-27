package com.example.testing;

public class Card {
    public String NAME;
    public String COLOR;
    public String VALUE;
    public String IMAGEPATH;
    public String BACKPATH;

    public Card(String name, String color, String value, String imagePath, String backPath){
        this.NAME = name;
        this.COLOR = color;
        this.VALUE = value;
        this.IMAGEPATH = imagePath;
        this.BACKPATH = backPath;
    }
}
