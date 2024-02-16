package com.example.myapplication.repository.models;

public class GetNumberRequest {
    int room_id;
    String chek_in_date;
    public GetNumberRequest(int room_id, String chek_in_date){
        this.room_id=room_id;
        this.chek_in_date=chek_in_date;
    }
}
