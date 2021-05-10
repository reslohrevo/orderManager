package com.zog3.ordermanager;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Component
public class KafkaService {
    private String topic = "payment-events";
    private KafkaProducer producer = null;

    @PostConstruct
    public void foo() throws UnknownHostException {
        Properties config = new Properties();
        config.setProperty("client.id", InetAddress.getLocalHost().getHostName());
        config.setProperty("bootstrap.servers", "localhost:9092");
        config.setProperty("acks", "0");
        config.setProperty("key.serializer", EventSerializer.class.getName());
        config.setProperty("value.serializer", EventSerializer.class.getName());
        producer = new KafkaProducer(config);
    }

    public <T extends ApplicationEvent> void send(T event) {
        System.out.println("KAFKA EVENT SENT " + event);
        producer.send(new ProducerRecord(topic, event));
        // Not caring about the result.
    }
}
