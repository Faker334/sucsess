package com.example.myapplication;

public enum ModelObject {
    RED(R.string.red, R.layout.view_red),
    BLUE(R.string.blue, R.layout.view_blue),
    GREEN(R.string.green, R.layout.view_green);

    private  int mTitleResID;
    private  int mLayoutResID;
    ModelObject(int TitleResID, int LayoutResID){
        mLayoutResID= LayoutResID;
        mTitleResID=TitleResID;
    }
    public int getmTitleResID(){
        return mTitleResID;
    }
    public int getmLayoutResID(){
        return mLayoutResID;
    }


}
