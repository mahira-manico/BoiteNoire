package com.laplateforme.boitenoire.controller;
import com.laplateforme.boitenoire.dto.FunnelConversionDTO;
import com.laplateforme.boitenoire.services.AnalyticService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")

public class AnalyticController {

    private final AnalyticService analyticService;

    public AnalyticController(AnalyticService analyticService){
        this.analyticService=analyticService;
    }

    @GetMapping("/top-users")
    public ResponseEntity<List<TopTenUserDTO>> getTopTenUsers(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant endDate)
    {
        return ResponseEntity.ok(AnalyticService.getTopTenUsers(startDate,endDate));
    }


    @GetMapping("/errors-repartitions")
    public ResponseEntity<List<ErrorsRepartitionDTO>> getErrorsRepartition(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant endDate){
        return ResponseEntity.ok(AnalyticService.getErrorsRepartition(startDate, endDate));
    }


    @GetMapping("/answer-time")
    public ResponseEntity<List<AnswerTimeDTO>> getAnswerTime(){
        return ResponseEntity.ok(AnalyticService.getAnswerTime());
    }


    @GetMapping("/funnel-conversion")
    public ResponseEntity<List<FunnelConversionDTO>> getFunnelConversion(){
        return ResponseEntity.ok(AnalyticService.getFunnelConversion());
    }



}
