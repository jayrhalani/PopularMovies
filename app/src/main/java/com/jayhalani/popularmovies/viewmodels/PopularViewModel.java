package com.jayhalani.popularmovies.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayhalani.popularmovies.model.movies.Movie;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.utils.NetworkUtils;

import java.util.List;
import java.util.Objects;

public class PopularViewModel extends AndroidViewModel {
    
    private MutableLiveData<List<MovieResults>> movieResults = new MutableLiveData<>();
    
    public PopularViewModel(@NonNull Application application) {
        super(application);
        StringRequest request = new StringRequest(
                new NetworkUtils().buildMovieListUrl(Constants.SORT_BY_POPULAR),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Movie movie = gson.fromJson(response, Movie.class);
                        movieResults.setValue(movie.getResults());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(application));
        requestQueue.add(request);
    }
    
    public LiveData<List<MovieResults>> getMovieResults() {
        return movieResults;
    }
}
