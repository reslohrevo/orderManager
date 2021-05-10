package com.zog3.ordermanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EventSerializer<ApplicationEvent> implements Serializer<ApplicationEvent> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    public byte[] serialize(String arg0, ApplicationEvent arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
    public void close() {
    }
}
