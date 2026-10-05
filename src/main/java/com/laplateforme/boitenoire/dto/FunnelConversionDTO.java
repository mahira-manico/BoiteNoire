package com.laplateforme.boitenoire.dto;

//DTO to get the funnel of user's steps
public record FunnelConversionDTO(String stepName, long count, double conversionRatePercentage) {}
