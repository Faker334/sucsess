package com.example.myapplication.view.adapter;

public class OptionsServis {
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    int type;
    String name;
    String[] values;

    public OptionsServis(Integer type, String name, String[] values) {
        this.type = type;
        this.name = name;
        this.values = values;
    }
}
