package com.laplateforme.boitenoire.dto;

//DTO to get answer time in ms
public record AnswerTimeDTO(
        String endpoint,
        Double averageResponseTimeMs,
        Double percentile95ResponseTimeMs
) {
}