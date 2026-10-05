package com.laplateforme.boitenoire.model.details;

//Under class of ApiCall
public class ApiDetails {

    private String httpMethod;
    private String endpoint;
    private Integer responseTimeMs;

    //Empty constructor for spring data
    public ApiDetails(){}

    //Normal constructor
    public ApiDetails(String httpMethod,String endpoint,Integer responseTimeMs){
        this.httpMethod=httpMethod;
        this.endpoint=endpoint;
        this.responseTimeMs=responseTimeMs;
    }


    //Getters and Setters
    public String getHttpMethod() {
        return httpMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setResponseTimeMs(Integer responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }
}
