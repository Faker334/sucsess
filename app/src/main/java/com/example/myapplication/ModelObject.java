package com.example.myapplication;

public enum ModelObject {
    PERVAYA(R.string.red, R.layout.view_one),
    VTORAYA(R.string.blue, R.layout.view_to),
    TRETYA(R.string.green, R.layout.view_trii);

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
