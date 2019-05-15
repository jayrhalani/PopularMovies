package com.jayhalani.popularmovies.model.movies;

import android.arch.lifecycle.LiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    
    @SerializedName("results")
    @Expose
    private final List<MovieResults> results = null;
    
    public List<MovieResults> getResults() {
        return results;
    }
}
