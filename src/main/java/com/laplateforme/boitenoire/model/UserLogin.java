package com.laplateforme.boitenoire.model;

import java.time.Instant;

//under class of parent class Event
public class UserLogin extends Event{

    private final String ipAddress;
    private final String device;

    public UserLogin(Instant timestamps, EventType eventType, String userId, String ipAddress, String device){
        super(timestamps,eventType,userId);
        this.ipAddress=ipAddress;
        this.device=device;
    }

    //Getters and Setters
    @Override
    public Instant getTimestamps() {
        return super.getTimestamps();
    }

    @Override
    public EventType getEventType() {
        return super.getEventType();
    }

    @Override
    public String getUserId() {
        return super.getUserId();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDevice() {
        return device;
    }

    @Override
    public void setEventType(EventType eventType) {
        super.setEventType(eventType);
    }
}
