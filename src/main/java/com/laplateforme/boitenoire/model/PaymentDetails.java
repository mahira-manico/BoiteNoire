package com.laplateforme.boitenoire.model;

public class PaymentDetails {

    private double amount;
    private Status status;

    public PaymentDetails(){};

    public PaymentDetails(double amount, Status status){
        this.amount=amount;
        this.status=status;
    }

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
