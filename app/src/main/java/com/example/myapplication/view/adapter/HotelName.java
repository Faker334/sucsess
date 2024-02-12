package com.example.myapplication.view.adapter;
import com.example.myapplication.R;

import java.io.Serializable;

public class HotelName implements Serializable {

    private String name;
    private int image;

    public int getIdroom() {
        return idroom;
    }

    public void setIdroom(int idroom) {
        this.idroom = idroom;
    }

    private int idroom;

    public HotelName(String s,int idroom) {
        this.idroom=idroom;
        this.name=s;
        this.image= R.drawable.krovat;
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