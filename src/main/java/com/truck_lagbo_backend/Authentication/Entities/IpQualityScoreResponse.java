package com.truck_lagbo_backend.Authentication.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpQualityScoreResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("vpn")
    private boolean vpn;

    @JsonProperty("proxy")
    private boolean proxy;

    @JsonProperty("tor")
    private boolean tor;

    @JsonProperty("active_vpn")
    private boolean activeVpn;

    @JsonProperty("active_tor")
    private boolean activeTor;

    @JsonProperty("data_center")
    private boolean dataCenter;

    @JsonProperty("fraud_score")
    private int fraudScore;

    // Getters and Setters
    public boolean isVpn() {
        return vpn;
    }

    public void setVpn(boolean vpn) {
        this.vpn = vpn;
    }

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }

    public boolean isDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(boolean dataCenter) {
        this.dataCenter = dataCenter;
    }

    public int getFraudScore() {
        return fraudScore;
    }

    public void setFraudScore(int fraudScore) {
        this.fraudScore = fraudScore;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
