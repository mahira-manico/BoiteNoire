package com.laplateforme.boitenoire.model;

import java.time.Instant;

// Under class of parent class Event
public class ApiCall extends Event {

    private String httpMethod;
    private String endpoint;
    private Integer responseTimeMs;

    public ApiCall(Instant timestamps, EventType eventType, String userId,
                String httpMethod, String endpoint, Integer responseTimeMs) {
        super(timestamps, eventType, userId);
        this.httpMethod = httpMethod;
        this.endpoint = endpoint;
        this.responseTimeMs = responseTimeMs;
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

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getResponseTimeMs() {
        return responseTimeMs;
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

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setResponseTimeMs(Integer responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }
}