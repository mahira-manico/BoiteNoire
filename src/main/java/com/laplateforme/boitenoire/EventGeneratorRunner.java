package com.laplateforme.boitenoire;

import com.laplateforme.boitenoire.service.EventGeneratorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EventGeneratorRunner implements CommandLineRunner {

    private final EventGeneratorService eventGeneratorService;

    public EventGeneratorRunner(EventGeneratorService eventGeneratorService) {
        this.eventGeneratorService = eventGeneratorService;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("--generate-events")) {
            eventGeneratorService.generateEvents();
        }
    }
}