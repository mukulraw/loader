package com.onnway.onnway.confirm_full_POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lr {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("assign_id")
    @Expose
    private String assignId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("name2")
    @Expose
    private String name2;

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

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}
