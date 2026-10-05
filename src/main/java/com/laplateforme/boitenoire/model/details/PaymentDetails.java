package com.laplateforme.boitenoire.model.details;

import com.laplateforme.boitenoire.model.events.Status;

//Under class of class Payment
public class PaymentDetails {

    private double amount;
    private Status status;

    public PaymentDetails(){};

    public PaymentDetails(double amount, Status status){
        this.amount=amount;
        this.status=status;
    }

    //Getters and Setters
    public double getAmount() {
        return amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
