package com.onnway.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class loaderCountBean {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("waitingdata")
    @Expose
    private Integer waitingdata;
    @SerializedName("ordersdata")
    @Expose
    private Integer ordersdata;
    @SerializedName("quotesdata")
    @Expose
    private Integer quotesdata;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getWaitingdata() {
        return waitingdata;
    }

    public void setWaitingdata(Integer waitingdata) {
        this.waitingdata = waitingdata;
    }

    public Integer getOrdersdata() {
        return ordersdata;
    }

    public void setOrdersdata(Integer ordersdata) {
        this.ordersdata = ordersdata;
    }

    public Integer getQuotesdata() {
        return quotesdata;
    }

    public void setQuotesdata(Integer quotesdata) {
        this.quotesdata = quotesdata;
    }
}
