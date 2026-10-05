package com.laplateforme.boitenoire.dto;

//DTO to get errors distributions by errors type and date
public record ErrorsDistributionDTO(String date, String errorType, long count) {
}
