package com.laplateforme.boitenoire.model;

import java.time.Instant;

// Under class of parent class Event
public class ApiCall extends Event {

    private final ApiDetails apiDetails;

    public ApiCall(Instant timestamps, EventType eventType, String userId, ApiDetails apiDetails) {
        super(timestamps, eventType, userId);
        this.apiDetails=apiDetails;

    }

    // Getters and Setters
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

    @Override
    public void setTimestamps(Instant timestamps) {
        super.setTimestamps(timestamps);
    }

    @Override
    public void setEventType(EventType eventType) {
        super.setEventType(eventType);
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    public ApiDetails getApiDetails() {
        return apiDetails;
    }
}