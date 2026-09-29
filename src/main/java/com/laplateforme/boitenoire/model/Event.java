package com.laplateforme.boitenoire.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

//Parent class, main class of collection "events"
@Document(collection = "events")
public class Event {
    @Id
    private String id; //unique id for mongoDB
    private Instant timestamps; //native java class for timestamps
    private EventType eventType; //enum event type
    private String userId;  //Reference userId

    public Event(){} //empty constructor for Spring Data

    //Constructor
    public Event(Instant timestamps, EventType eventType, String userId){
        this.timestamps=timestamps;
        this.eventType=eventType;
        this.userId=userId;
    }

    //Getters and Setters
    public Instant getTimestamps() {
        return timestamps;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getUserId() {
        return userId;
    }

    public void setTimestamps(Instant timestamps) {
        this.timestamps = timestamps;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
