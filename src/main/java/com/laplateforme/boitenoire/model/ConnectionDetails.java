package com.laplateforme.boitenoire.model;

public class ConnectionDetails {

    private String ipAddress;
    private String device;

    public ConnectionDetails(){};

    public ConnectionDetails(String ipAddress, String device) {
        this.ipAddress =ipAddress;
        this.device=device;
    }

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
