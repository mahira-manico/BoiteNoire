package com.laplateforme.boitenoire.model;

import java.time.Instant;

//under class payment inheriting of parent class Event
public class Payment extends Event{
    private double amount;
    private Status status;

    public Payment(Instant timestamps, EventType eventType, String userId, double amount,Status status){
        super(timestamps, eventType, userId); //inheritance
        this.amount=amount;
        this.status=status;
    }

    //getters and setters
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

    public double getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
