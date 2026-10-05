package com.laplateforme.boitenoire.model.details;

//Under class of class UserLogin
public class ConnectionDetails {

    private String ipAddress;
    private String device;

    public ConnectionDetails(){};

    public ConnectionDetails(String ipAddress, String device) {
        this.ipAddress =ipAddress;
        this.device=device;
    }

    //Getters and Setters
    public String getIpAddress() {
        return ipAddress;
    }

    public String getDevice() {
        return device;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
