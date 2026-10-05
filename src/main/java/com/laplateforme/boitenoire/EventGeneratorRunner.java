package com.laplateforme.boitenoire;

import com.laplateforme.boitenoire.service.EventGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EventGeneratorRunner implements CommandLineRunner {

    //Take the generator service
    private final EventGeneratorService eventGeneratorService;

    public EventGeneratorRunner(EventGeneratorService eventGeneratorService) {
        this.eventGeneratorService = eventGeneratorService;
    }

    //Run the generation
    @Override
    public void run(String... args) {

        boolean shouldGenerate = Arrays.asList(args).contains("--generate-events"); //Check if command exist with boolean

        if (shouldGenerate) {
            System.out.println("Start of generator...");
            eventGeneratorService.generateEvents();

            System.out.println("Generation done with success !");
            System.exit(0);
        }
    }
}