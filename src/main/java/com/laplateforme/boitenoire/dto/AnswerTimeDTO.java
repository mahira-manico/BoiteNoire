package com.laplateforme.boitenoire.dto;

public record AnswerTimeDTO(
        String endpoint,
        double averageResponseTimeMs,
        double percentile95ResponseTimeMs
) {
}