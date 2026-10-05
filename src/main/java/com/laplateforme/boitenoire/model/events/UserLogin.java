package com.laplateforme.boitenoire.model.events;

import com.laplateforme.boitenoire.model.details.ConnectionDetails;
import com.laplateforme.boitenoire.model.Event;
import com.laplateforme.boitenoire.model.EventType;

import java.time.Instant;

//under class of parent class Event
public class UserLogin extends Event {

   private ConnectionDetails connectionDetails; //Embedding

    public UserLogin(){
        super();
    }

    public UserLogin(Instant timestamps, EventType eventType, String userId, ConnectionDetails connectionDetails){
        super(timestamps,eventType,userId);
        this.connectionDetails=connectionDetails;
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

    public ConnectionDetails getConnectionDetails() {
        return connectionDetails;
    }

    public void setConnectionDetails(ConnectionDetails connectionDetails) {
        this.connectionDetails = connectionDetails;
    }

    @Override
    public void setEventType(EventType eventType) {
        super.setEventType(eventType);
    }
}
