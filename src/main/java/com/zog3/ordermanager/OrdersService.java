package com.zog3.ordermanager;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;

@Service
public class OrdersService {
    private Map<String, BigDecimal> prices = new HashMap<>();
    private Map<String, BigDecimal> discounts = new HashMap<>();
    private Map<String, BigDecimal> discountThreshold = new HashMap<>();

    @PostConstruct
    private void foo() {
        //Assigning prices to items
        prices.put("apple", BigDecimal.valueOf(0.60f).setScale(2, RoundingMode.FLOOR));
        prices.put("orange", BigDecimal.valueOf(0.25f).setScale(2, RoundingMode.FLOOR));

        //Assigning discount values to items
        discounts.put("apple", BigDecimal.valueOf(0.50f).setScale(2, RoundingMode.FLOOR));
        discounts.put("orange", BigDecimal.valueOf(0.67f).setScale(2, RoundingMode.FLOOR));

        //Modulus values establishing discount threshold
        discountThreshold.put("apple", BigDecimal.valueOf(2));
        discountThreshold.put("orange", BigDecimal.valueOf(3));
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
            BigDecimal itemPrice = prices.get(item).setScale(2);
            BigDecimal itemQuantity = BigDecimal.valueOf(quantity);
            BigDecimal discount = discounts.get(item);
            BigDecimal modulus = discountThreshold.get(item);
            BigDecimal remainder = itemQuantity.remainder(modulus);
            BigDecimal adjustedTotal = itemQuantity.subtract(remainder).setScale(2);
            BigDecimal discountedTotal = itemPrice.multiply(adjustedTotal).multiply(discount).setScale(2, RoundingMode.FLOOR);
            BigDecimal remainderTotal = remainder.multiply(itemPrice).setScale(2);
            BigDecimal itemTotal = discountedTotal.add(remainderTotal).setScale(2, RoundingMode.FLOOR);
            total = total.add(itemTotal);
        }

        OrderCheckout orderCheckout = new OrderCheckout(items, total);
        System.out.println(orderCheckout.toString());
        return orderCheckout;
    }
}