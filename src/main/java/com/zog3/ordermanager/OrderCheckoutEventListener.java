package com.zog3.ordermanager;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderCheckoutEventListener implements ApplicationListener<OrderCheckoutEvent> {
    private static final Logger log = Logger.getLogger("CustomSpringEventListener");
    @Override
    public void onApplicationEvent(OrderCheckoutEvent event) {
        log.info("Order completed - " + event.getMessage());
    }
}
