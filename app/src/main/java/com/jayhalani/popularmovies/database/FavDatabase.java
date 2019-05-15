package com.jayhalani.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {
    
    private static final String LOG_TAG = FavDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favoritedb";
    private static FavDatabase sInstance;
    
    public static FavDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new Database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavDatabase.class,
                        FavDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the Database instance");
        return sInstance;
    }
    
    public abstract FavoriteDao favoriteDao();
}
