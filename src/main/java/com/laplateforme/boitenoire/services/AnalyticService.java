package com.laplateforme.boitenoire.services;
import com.laplateforme.boitenoire.dto.FunnelConversionDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AnalyticService {

    private final MongoTemplate mongoTemplate;

    public AnalyticService(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<TopTenUserDTO> getTopTenUsers(Instant startDate, Instant endDate){
        Aggregation aggregation=newAggregation(
                match(Criteria.where("timestamp").gte(startDate).lte(endDate)),
                group("userId").count().as("eventCount"),
                project("eventCount").and("_id").as("userId"),
                sort(Sort.Direction.DESC,"eventCount"),
                limit(10));

        AggregationResults<TopTenUserDTO> results=mongoTemplate.aggregate(aggregation,"event",TopTenUserDTO.class);
        return results.getMappedResults();
    }

    public List<ErrorsRepartitionDTO> getErrorsRepartition(Instant startDate, Instant endDate) {
        Aggregation aggregation=newAggregation(
                match(Criteria.where("timestamp").gte(startDate).lte(endDate).and("EventType").is("ERROR")),
                project("ErrorDetails.errorType").and(DateOperators.dateOf("timestamp").toString("%Y-%m-%d")).as("day"),
                group("day","errorType").count().as("count"),
                project("count").and("_id.day").as("date").and("_id.errorType").as("errorType"),
                sort(Sort.Direction.DESC,"date"));

        AggregationResults<ErrorsRepartitionDTO> results=mongoTemplate.aggregate(aggregation,"event",TopTenUserDTO.class);
        return results.getMappedResults();
    }

    public List<AnswerTimeDTO> getAnswerTime() {
    }

    public List<FunnelConversionDTO> getFunnelConversion() {
    }

}
