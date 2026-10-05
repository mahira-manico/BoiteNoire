package com.laplateforme.boitenoire.model;

import java.time.Instant;

// Under class of parent class Event
public class Error extends Event {

    private final ErrorsDetails errorsDetails;

    public Error(Instant timestamps, EventType eventType, String userId, ErrorsDetails errorsDetails) {
        super(timestamps, eventType, userId);
        this.errorsDetails = errorsDetails;
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

    public ErrorsDetails getErrorsDetails() {
        return errorsDetails;
    }
}

