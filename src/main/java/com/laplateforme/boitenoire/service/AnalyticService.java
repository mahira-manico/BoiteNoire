package com.laplateforme.boitenoire.service;
import com.laplateforme.boitenoire.dto.AnswerTimeDTO;
import com.laplateforme.boitenoire.dto.ErrorsDistributionDTO;
import com.laplateforme.boitenoire.dto.FunnelConversionDTO;
import com.laplateforme.boitenoire.dto.TopTenUserDTO;
import org.bson.Document;
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

    private final MongoTemplate mongoTemplate; //Import mongo template to use aggregations

    public AnalyticService(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }


    //Get the top ten with aggregations
    public List<TopTenUserDTO> getTopTenUsers(Instant startDate, Instant endDate){
        Aggregation aggregation=newAggregation(
                match(Criteria.where("timestamps").gte(startDate).lte(endDate)),
                group("userId").count().as("eventCount"),
                project("eventCount").and("_id").as("userId"),
                sort(Sort.Direction.DESC,"eventCount"),
                limit(10));

        AggregationResults<TopTenUserDTO> results=mongoTemplate.aggregate(aggregation,"events",TopTenUserDTO.class);
        return results.getMappedResults();
    }

    //Get the Errors filtered by date and evenType using aggregations
    public List<ErrorsDistributionDTO> getErrorsRepartition(Instant startDate, Instant endDate) {
        Aggregation aggregation=newAggregation(
                match(Criteria.where("timestamps").gte(startDate).lte(endDate).and("eventType").is("ERROR")),
                project("errorsDetails.errorType")
                        .and(DateOperators.DateToString.dateOf("timestamps").toString("%Y-%m-%d")).as("day"),
                group("day","errorType").count().as("count"),
                project("count").and("_id.day").as("date").and("_id.errorType").as("errorType"),
                sort(Sort.Direction.DESC,"date"));

        AggregationResults<ErrorsDistributionDTO> results=mongoTemplate.aggregate(aggregation,"events",ErrorsDistributionDTO.class);
        return results.getMappedResults();
    }

    //Get the answer time of api call
    public List<AnswerTimeDTO> getAnswerTime() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("eventType").is("API_CALL")),
                sort(Sort.Direction.ASC, "apiDetails.responseTimeMs"),
                group("apiDetails.endpoint")
                        .avg("apiDetails.responseTimeMs").as("averageResponseTimeMs")
                        .push("apiDetails.responseTimeMs").as("durations"),
                project("averageResponseTimeMs", "durations")
                        .and("_id").as("endpoint"),
                sort(Sort.Direction.ASC, "endpoint")
        );

        var results = mongoTemplate.aggregate(aggregation, "events", org.bson.Document.class).getMappedResults();

        //Take the result and do a loop on all docs
        return results.stream().map(doc -> {
            String endpoint = doc.getString("endpoint"); //Take endpoints
            Number avgNum = (Number) doc.get("averageResponseTimeMs"); //Take average
            double avg = (avgNum != null) ? Math.round(avgNum.doubleValue() * 100.0) / 100.0 : 0.0; //Filter average to get a double
            List<?> durations = doc.getList("durations", Number.class); //Take the list of durations
            double p95 = 0.0;
            if (durations != null && !durations.isEmpty()) { //Get the p95
                int index = (int) Math.ceil(0.95 * durations.size()) - 1;
                index = Math.max(0, Math.min(index, durations.size() - 1));
                Number p95Num = (Number) durations.get(index);
                p95 = (p95Num != null) ? p95Num.doubleValue() : 0.0;
            }

            return new AnswerTimeDTO(endpoint, avg, p95);
        }).toList();
    }

    //Get the steps of a user
    public List<FunnelConversionDTO> getFunnelConversion() {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("eventType").in("USER_LOGIN", "NOTIFICATION", "PAYMENT")),
                group("userId").addToSet("eventType").as("actions")
        );

        List<Document> userActions = mongoTemplate.aggregate(aggregation, "events", Document.class)
                .getMappedResults();

        long step1Count = 0;
        long step2Count = 0;
        long step3Count = 0;

        for (Document doc : userActions) {
            List<?> actions = doc.get("actions", List.class);
            if (actions != null) {
                boolean hasLogin = actions.contains("USER_LOGIN");
                boolean hasNotification = actions.contains("NOTIFICATION");
                boolean hasPayment = actions.contains("PAYMENT");

                if (hasLogin) {
                    step1Count++;
                    if (hasNotification) {
                        step2Count++;
                        if (hasPayment) {
                            step3Count++;
                        }
                    }
                }
            }
        }

        double rate1 = step1Count > 0 ? 100.0 : 0.0;
        double rate2 = step1Count > 0 ? Math.round(((double) step2Count / step1Count) * 10000.0) / 100.0 : 0.0;
        double rate3 = step1Count > 0 ? Math.round(((double) step3Count / step1Count) * 10000.0) / 100.0 : 0.0;

        return List.of(
                new FunnelConversionDTO("Connection (USER_LOGIN)", step1Count, rate1),
                new FunnelConversionDTO("Message sent (NOTIFICATION)", step2Count, rate2),
                new FunnelConversionDTO("Subscription (PAYMENT)", step3Count, rate3)
        );
    }

}
