package com.zog3.ordermanager;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

@Service
public class OrdersService {
    private Map<String, BigDecimal> prices = new HashMap<>();

    @PostConstruct
    private void foo() {
        prices.put("apple", BigDecimal.valueOf(0.60));
        prices.put("orange", BigDecimal.valueOf(0.25));
    }

    public OrderCheckout processOrder(Order request) {
        Map<String, Integer> items = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO.setScale(2);

        request.getItems()
                .stream()
                .filter(s -> prices.containsKey(s))
                .forEach(
                l -> {
                    String item = l.toLowerCase();
                    if(items.containsKey(item)) {
                        Integer quantity = items.get(item);
                        items.replace(item, quantity + 1);
                    } else {
                        items.put(item, 1);
                    }
                }
        );

        for (String item: items.keySet()) {
            Integer quantity = items.get(item);
            BigDecimal itemTotal = prices.get(item).multiply(BigDecimal.valueOf(quantity));
            total = total.add(itemTotal);
        }

        OrderCheckout orderCheckout = new OrderCheckout(items, total);
        System.out.println(orderCheckout.toString());
        return orderCheckout;
    }
}