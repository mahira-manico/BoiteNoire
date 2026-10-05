package com.laplateforme.boitenoire.model;

import java.time.Instant;

// Under class of parent class Event
public class Notification extends Event {

    private String channelId;

    public Notification(Instant timestamps, EventType eventType, String userId, String channelId) {
        super(timestamps, eventType, userId);
        this.channelId = channelId;
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

    public String getChannelId() {
        return channelId;
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

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}