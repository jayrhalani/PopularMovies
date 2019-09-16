package com.jayhalani.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorite")
public class Favorite implements Serializable {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "movie_id")
    private int movieId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;
    @ColumnInfo(name = "vote_count")
    private Integer voteCount;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @Ignore
    public Favorite(int movieId, String title, String posterPath, String backdropPath, Double voteAverage, Integer voteCount, String overview, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    
    public Favorite(int id, int movieId, String title, String posterPath, String backdropPath, Double voteAverage, Integer voteCount, String overview, String releaseDate) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
   
    
    public int getId() {
        return id;
    }
    
    public int getMovieId() {
        return movieId;
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
    
    public Double getVoteAverage() {
        return voteAverage;
    }
    
    public Integer getVoteCount() {
        return voteCount;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public String getReleaseDate() {
        return releaseDate;
    }
}
