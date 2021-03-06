package com.jayhalani.popularmovies.model.reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("id")
    @Expose
    private Integer id;
    
    @SerializedName("results")
    @Expose
    private List<ReviewResult> results = null;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public List<ReviewResult> getResults() {
        return results;
    }
}
