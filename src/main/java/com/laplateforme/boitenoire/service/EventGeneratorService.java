package com.laplateforme.boitenoire.service;

import com.laplateforme.boitenoire.model.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import com.laplateforme.boitenoire.model.Error;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EventGeneratorService {

    private static  final int NUMBER_OF_EVENTS = 100_000;

    private final EventRepository eventRepository ;

    private final Random random = new Random();

    public EventGeneratorService(EventRepository eventRepository) {

        this.eventRepository = eventRepository;
    }

    private  Instant generateRandomTimestamp() {
        Instant endDate = Instant.now();

        Instant startDate = endDate.minus(365, ChronoUnit.DAYS);

        long randomSeconds = random.nextLong(
            startDate.getEpochSecond(),
            endDate.getEpochSecond()
        );

        Instant timestamp =  Instant.ofEpochSecond(randomSeconds);

        java.time.DayOfWeek day =
                timestamp.atZone(java.time.ZoneOffset.UTC).getDayOfWeek();
        
        int hour =
                timestamp.atZone(java.time.ZoneOffset.UTC).getHour();

        boolean weekend =
                    day == java.time.DayOfWeek.SATURDAY ||
                    day == java.time.DayOfWeek.SUNDAY;


        // Less activity during the night
        if (hour < 8 || hour >= 22) {
            if (random.nextInt(4) != 0) {
                return generateRandomTimestamp();
            }

        }

         // Less activity during the weekend
        if (weekend) {
            if (random.nextInt(3) != 0) {
                return generateRandomTimestamp();
            }
        }
        return timestamp;

    }

    private  EventType generateEventType() {

        EventType[] eventTypes = EventType.values();

        int randomIndex =random.nextInt(eventTypes.length);

        return  eventTypes[randomIndex];

    }

    private String generateUserId() {

        int userNumber ;
        // A few users generate much more activity
        if (random.nextInt(10) == 0) {
            userNumber = 1 + random.nextInt(9);
        } else {
            userNumber = 10 + random.nextInt(990);
        }

        return "user-" + userNumber;

    }


    private Event generateEvent() {

        Instant timestamp = generateRandomTimestamp();

        EventType eventType = generateEventType();

        String userId = generateUserId();

    switch (eventType) {

        case USER_LOGIN:
              // create UserLogin event

            String ipAddress = "192.168.1." + random.nextInt(255);
            String device = "Chrome";
            ConnectionDetails connectionDetails=new ConnectionDetails(ipAddress,device);


            return new UserLogin(
                    timestamp,
                    eventType,
                    userId,
                    connectionDetails
            );
        
        
        case PAYMENT: 

            double amount = 10 + random.nextDouble() * 90;

            Status[] statuses = Status.values();
            int randomStatusIndex = random.nextInt(statuses.length);
            Status status =statuses[randomStatusIndex];
            PaymentDetails paymentDetails=new PaymentDetails(amount,status);

            return new Payment(
                    timestamp,
                    eventType,
                    userId,
                    paymentDetails
            );

        case API_CALL:
            String[] httpMethods = {"GET", "POST", "PUT", "DELETE"};
            String httpMethod = httpMethods[random.nextInt(httpMethods.length)];

            String[] endpoints = {
                    "/api/users",
                    "/api/messages",
                    "/api/payments",
                    "/api/notifications"
            };
            String endpoint = endpoints[random.nextInt(endpoints.length)];

            Integer responseTimeMs = 50 + random.nextInt(951);

            ApiDetails apiDetails=new ApiDetails(httpMethod,endpoint,responseTimeMs);


            return new ApiCall(
                    timestamp,
                    eventType,
                    userId,
                    apiDetails
            );

        case NOTIFICATION:

            String[] channels = {
                "email",
                "sms",
                "push"
            };

            String channelId = channels[random.nextInt(channels.length)];

            return new Notification(
                    timestamp,
                    eventType,
                    userId,
                    channelId

            );

        case ERROR:

            String[] errorTypes = {
                "AUTHENTICATION_ERROR",
                "DATABASE_ERROR",
                "VALIDATION_ERROR",
                "SERVER_ERROR"
            };

            String errorType = errorTypes[random.nextInt(errorTypes.length)];

            String[] errorMessages = {

                "Invalid credentials",
                "Database connection failed",
                "Invalid request data",
                "Internal server error"

            };

            String errorMessage = errorMessages[random.nextInt(errorMessages.length)];
            ErrorsDetails errorsDetails=new ErrorsDetails(errorType,errorMessage);

            return new Error(
                    timestamp,
                    eventType,
                    userId,
                   errorsDetails
            );

        default:
            throw new IllegalStateException("Unknown event type: " + eventType);


    
        }
    }

    public void generateEvents() {
        
        eventRepository.deleteAll();

        int batchSize = 1000;

        for (int i = 0; i < NUMBER_OF_EVENTS; i += batchSize) {

            List<Event> events = new ArrayList<>();

            int end = Math.min(i + batchSize, NUMBER_OF_EVENTS);

            for (int j = i; j < end; j++) {
                events.add(generateEvent());
            }

            eventRepository.saveAll(events);


            System.out.println(end + " events generated");

        }
    }

}
