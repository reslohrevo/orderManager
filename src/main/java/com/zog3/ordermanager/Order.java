package com.zog3.ordermanager;

import java.util.List;

public class Order {
    private List<String> items;

    public Order(List<String> items) {
        this.items = items;
    }

    public Order() {

    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                '}';
    }
}
