package com.zog3.ordermanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class OrdersService {
    private static final Logger log = Logger.getLogger("OrdersService");
    private Map<String, BigDecimal> prices = new HashMap<>();
    private Map<String, BigDecimal> discounts = new HashMap<>();
    private Map<String, Integer> discountThreshold = new HashMap<>();
    private Boolean deals = true;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostConstruct
    private void foo() {
        //Assigning prices to items
        prices.put("apple", BigDecimal.valueOf(0.60f));
        prices.put("orange", BigDecimal.valueOf(0.25f));

        //Assigning discount values to items
        discounts.put("apple", BigDecimal.valueOf(0.50f));
        discounts.put("orange", BigDecimal.valueOf(0.67f));

        //Modulus values establishing discount threshold
        discountThreshold.put("apple", 2);
        discountThreshold.put("orange", 3);
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
                            if (items.containsKey(item)) {
                                Integer quantity = items.get(item);
                                items.replace(item, quantity + 1);
                            } else {
                                items.put(item, 1);
                            }
                        }
                );

        for (String item : items.keySet()) {
            total = total.add(computeSubtotal(item, items));
        }

        OrderCheckout orderCheckout = new OrderCheckout(items, total);
        System.out.println(orderCheckout.toString());
        publisher.publishEvent(new OrderCheckoutEvent(this, orderCheckout.toString()));
        return orderCheckout;
    }

    private BigDecimal computeSubtotal(String item, Map<String, Integer> items) {
        BigDecimal itemTotal;
        if (deals) {
            BigDecimal remainder = BigDecimal.valueOf(items.get(item)).remainder(BigDecimal.valueOf(discountThreshold.get(item)));
            itemTotal = prices.get(item).multiply(BigDecimal.valueOf(items.get(item)).subtract(remainder)).multiply(discounts.get(item))//
                    .add(remainder.multiply(prices.get(item))).setScale(2, RoundingMode.FLOOR);
        } else {
            itemTotal = prices.get(item).multiply(BigDecimal.valueOf(items.get(item)));
        }
        return itemTotal;
    }
}