package com.example.myapplication.inventory;
import java.io.Serializable;

public class HotelName implements Serializable {

    private String name;
    private int image;

    public HotelName() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}