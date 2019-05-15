package com.jayhalani.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    
    @Query("SELECT * FROM favorite ORDER BY id")
    LiveData<List<Favorite>> loadAllMovies();
    
    @Insert
    void insertMovie(Favorite movie);
    
    @Query("SELECT * FROM favorite WHERE movie_id = :id")
    boolean hasMovieId(int id);
    
    @Query("DELETE FROM favorite WHERE movie_id = :id")
    void deleteMovieById(int id);
}
