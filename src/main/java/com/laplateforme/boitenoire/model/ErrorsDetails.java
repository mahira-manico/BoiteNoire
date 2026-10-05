package com.laplateforme.boitenoire.model;

//Under class of class Error
public class ErrorsDetails {

    private String errorType;
    private String errorMessage;

    public  ErrorsDetails(){}

    public ErrorsDetails(String errorType, String errorMessage){
        this.errorType=errorType;
        this.errorMessage=errorMessage;
    }

    //Getters and Setters
    public String getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
