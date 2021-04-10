package com.zog3.ordermanager;

import java.math.BigDecimal;
import java.util.Map;

public class OrderCheckout {
    private Map<String, Integer> items;
    private BigDecimal total;

    public OrderCheckout() {

    }

    public OrderCheckout(Map<String, Integer> items, BigDecimal total) {
        this.items = items;
        this.total = total;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "OrderCheckout{" +
                "items=" + items +
                ", total=" + total +
                '}';
    }

    public BigDecimal getTotal() {
        return total;
    }
}
