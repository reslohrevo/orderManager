package com.zog3.ordermanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrdersController {
    private static final Logger logger = LogManager.getLogger(OrdersController.class);


    @Autowired
    OrdersService ordersSvc;
    /*
        @GetMapping("/orders")          //get collection of order objects READ
        @GetMapping("/orders/{id}")     //get single order object by ID READ
        @DeleteMapping("/orders/{id}")  //delete single order object by ID DELETE
        @PutMapping("/orders/{id}")     //update single order object by ID UPDATE
        @PostMapping("/orders")         //create new order object in collection CREATE */
    @PostMapping("/orders")
    public ResponseEntity<OrderCheckout> addRow(@RequestBody Order request) {
        System.out.println("Request: " + request);
        try {
            return new ResponseEntity<OrderCheckout>(ordersSvc.processOrder(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<OrderCheckout>(HttpStatus.BAD_REQUEST);
        }
    }
}
