package com.jayhalani.popularmovies.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.jayhalani.popularmovies.database.FavDatabase;
import com.jayhalani.popularmovies.database.Favorite;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    
    private LiveData<List<Favorite>> favorites;
    
    public MainViewModel(@NonNull Application application) {
        super(application);
        FavDatabase mDatabase = FavDatabase.getInstance(this.getApplication());
        favorites = mDatabase.favoriteDao().loadAllMovies();
    }
    
    public LiveData<List<Favorite>> getFavorites() {
        return favorites;
    }
}
