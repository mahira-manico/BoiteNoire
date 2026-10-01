package com.laplateforme.boitenoire.model;

import org.springframework.data.mongodb.repository.MongoRepository;

//Event repository
public interface EventRepository extends MongoRepository<Event, String> { //Entity type "event" and primary key type "String"
}
