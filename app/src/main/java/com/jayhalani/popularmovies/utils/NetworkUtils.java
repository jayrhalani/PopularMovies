package com.jayhalani.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

public class NetworkUtils {
    
    private final Uri.Builder builder = new Uri.Builder();
    
    public NetworkUtils() {
    }
    
    // Build Url to get JSON for Movie List Sorted by, Popular/Favorite/...
    public String buildMovieListUrl(String sortBy) {
        builder.scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.TMDB_BASE_URL)
                .appendEncodedPath(Constants.API_VERSION)
                .appendEncodedPath(Constants.CONTENT_TYPE)
                .appendEncodedPath(sortBy)
                .appendQueryParameter(Constants.API_KEY_TEXT, Constants.API_KEY);
        
        return builder.build().toString();
    }
    
    // Build Url to get JSON for retrieving image for backdrops and Thumbnails
    public String builtImageUrl(String imagePath, String imageQuality) {
        builder.scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.IMAGES_BASE_URL)
                .appendEncodedPath(Constants.IMAGES_APPEND_T)
                .appendEncodedPath(Constants.IMAGES_APPEND_P)
                .appendEncodedPath(imageQuality)
                .appendEncodedPath(imagePath);
        
        return builder.build().toString();
    }
    
    // Build Url to get JSON for Trailers and Reviews
    public String buildUrlByMovieId(String sortBy, String itemId){
    
        builder.scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.TMDB_BASE_URL)
                .appendEncodedPath(Constants.API_VERSION)
                .appendEncodedPath(Constants.CONTENT_TYPE)
                .appendEncodedPath(itemId)
                .appendEncodedPath(sortBy)
                .appendQueryParameter(Constants.API_KEY_TEXT, Constants.API_KEY);
    
        return builder.build().toString();
    }
    
    // Build web url of Youtube from Youtube video key
    public String buildYoutubeWebUrl(String videoKey){
        builder.scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.YOUTUBE_BASE_URL)
                .appendEncodedPath(Constants.YOUTUBE_CONTENT_TYPE)
                .appendQueryParameter(Constants.YOUTUBE_APPEND_V, videoKey);
        
        return builder.build().toString();
    }
    
    // Check if network is connected or not
    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    
    
}
