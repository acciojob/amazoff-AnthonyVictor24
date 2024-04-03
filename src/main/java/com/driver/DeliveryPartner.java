package com.driver;

import java.util.ArrayList;
import java.util.List;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;
    private List<Order> orderList;

    public DeliveryPartner(String id) {
        this.id = id;
        this.numberOfOrders = 0;
        this.orderList=new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public String toString() {
        return "DeliveryPartner{" +
                "id='" + id + '\'' +
                ", numberOfOrders=" + numberOfOrders +
                ", orderList=" + orderList +
                '}';
    }
}
