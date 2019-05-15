package com.jayhalani.popularmovies.model.movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieResults implements Serializable {
  
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    
    @SerializedName("id")
    @Expose
    private Integer id;
    
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    
    @SerializedName("title")
    @Expose
    private String title;
    
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    
    @SerializedName("overview")
    @Expose
    private String overview;
    
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    
    public MovieResults(Integer movieId, String title, String posterPath, String backdropPath, Double voteAverage, Integer voteCount, String overview, String releaseDate) {
        this.voteCount = voteCount;
        this.id = movieId;
        this.voteAverage = voteAverage;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    
    public Integer getVoteCount() {
        return voteCount;
    }
    
    public Integer getId() {
        return id;
    }
    
    public Double getVoteAverage() {
        return voteAverage;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getPosterPath() {
        return posterPath;
    }
    
    public String getBackdropPath() {
        return backdropPath;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public String getReleaseDate() {
        return releaseDate;
    }
}
