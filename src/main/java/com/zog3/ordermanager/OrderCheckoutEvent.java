package com.zog3.ordermanager;

import org.springframework.context.ApplicationEvent;

public class OrderCheckoutEvent extends ApplicationEvent {
    private String message;

    public OrderCheckoutEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}