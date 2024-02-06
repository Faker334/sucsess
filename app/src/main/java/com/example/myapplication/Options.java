package com.example.myapplication;

import java.util.List;

public class Options {
    public String getTipe1_name() {
        return Tipe1_name;
    }

    public void setTipe1_name(String tipe1_name) {
        Tipe1_name = tipe1_name;
    }

    public String getTipe2name() {
        return Tipe2name;
    }

    public void setTipe2name(String tipe2name) {
        Tipe2name = tipe2name;
    }

    public String getTipe3name() {
        return Tipe3name;
    }

    public void setTipe3name(String tipe3name) {
        Tipe3name = tipe3name;
    }

    public String[] getTipe3value() {
        return Tipe3value;
    }

    public void setTipe3value(String[] tipe3value) {
        Tipe3value = tipe3value;
    }

    String Tipe1_name;
    String Tipe2name;
    String Tipe3name;
    String[] Tipe3value;
    Options(String tipe1_name, String tipe2name, String tipe3name, String[] tipe3value){
        this.Tipe1_name =tipe1_name;
        this.Tipe2name = tipe2name;
        this.Tipe3name =tipe3name;
        this.Tipe3value=tipe3value;
    }
}
