package com.jayhalani.popularmovies.model.trailers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrailerResult implements Serializable {
    
    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("key")
    @Expose
    private String key;
    
    @SerializedName("name")
    @Expose
    private String name;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
