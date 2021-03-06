package com.onnway.app.profilePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("af")
    @Expose
    private String af;
    @SerializedName("ab")
    @Expose
    private String ab;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("pan_verify")
    @Expose
    private String pan_verify;
    @SerializedName("af_verify")
    @Expose
    private String af_verify;
    @SerializedName("ab_verify")
    @Expose
    private String ab_verify;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAf() {
        return af;
    }

    public void setAf(String af) {
        this.af = af;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAb_verify() {
        return ab_verify;
    }

    public String getAf_verify() {
        return af_verify;
    }

    public String getPan_verify() {
        return pan_verify;
    }

    public void setPan_verify(String pan_verify) {
        this.pan_verify = pan_verify;
    }

    public void setAb_verify(String ab_verify) {
        this.ab_verify = ab_verify;
    }

    public void setAf_verify(String af_verify) {
        this.af_verify = af_verify;
    }
}
