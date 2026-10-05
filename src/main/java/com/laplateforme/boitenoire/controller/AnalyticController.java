package com.laplateforme.boitenoire.controller;
import com.laplateforme.boitenoire.dto.*;
import com.laplateforme.boitenoire.service.AnalyticService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;


//Import class
@RestController
//Url path
@RequestMapping("/api/analytics")
public class AnalyticController {

    //Import analytics service
    private final AnalyticService analyticService;

    //Constructor
    public AnalyticController(AnalyticService analyticService){
        this.analyticService=analyticService;
    }

    //All get controllers
    @GetMapping("/top-users")
    public ResponseEntity<List<TopTenUserDTO>> getTopTenUsers(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant endDate)
    {
        return ResponseEntity.ok(analyticService.getTopTenUsers(startDate,endDate));
    }


    @GetMapping("/errors-repartitions")
    public ResponseEntity<List<ErrorsDistributionDTO>> getErrorsRepartition(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) Instant endDate){
        return ResponseEntity.ok(analyticService.getErrorsRepartition(startDate, endDate));
    }


    @GetMapping("/answer-time")
    public ResponseEntity<List<AnswerTimeDTO>> getAnswerTime(){
        return ResponseEntity.ok(analyticService.getAnswerTime());
    }


    @GetMapping("/funnel-conversion")
    public ResponseEntity<List<FunnelConversionDTO>> getFunnelConversion(){
        return ResponseEntity.ok(analyticService.getFunnelConversion());
    }



}
