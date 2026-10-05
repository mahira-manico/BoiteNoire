package com.laplateforme.boitenoire.model;

import java.time.Instant;

//under class payment inheriting of parent class Event
public class Payment extends Event{

    private PaymentDetails paymentDetails;

    public Payment(Instant timestamps, EventType eventType, String userId, PaymentDetails paymentDetails){
        super(timestamps, eventType, userId); //inheritance
        this.paymentDetails=paymentDetails;
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

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
