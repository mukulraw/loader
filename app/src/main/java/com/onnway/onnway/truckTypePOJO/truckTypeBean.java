package com.onnway.onnway.truckTypePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class truckTypeBean {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("max_load")
    @Expose
    private String max_load;
    @SerializedName("created")
    @Expose
    private String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMax_load() {
        return max_load;
    }

    public void setMax_load(String max_load) {
        this.max_load = max_load;
    }
}
