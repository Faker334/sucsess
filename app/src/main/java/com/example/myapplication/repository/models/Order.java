package com.example.myapplication.repository.models;

public class Order {
    public Order(int OrderId, String nameOrder, String data, int status) {
        this.nameOrder = nameOrder;
        this.data = data;
        this.Status = status;
        this.orderId=OrderId;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public void setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



   int Status;

    String nameOrder;
    String data;

   int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getStatus() {
        return Status;
    }
}
