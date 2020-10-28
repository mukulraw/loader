package com.onnway.kedsons.confirm_full_POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pod {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("assign_id")
    @Expose
    private String assignId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignId() {
        return assignId;
    }

    public void setAssignId(String assignId) {
        this.assignId = assignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
