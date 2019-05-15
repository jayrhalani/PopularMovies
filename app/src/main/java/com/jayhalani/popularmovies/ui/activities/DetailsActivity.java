package com.jayhalani.popularmovies.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.adapters.ReviewAdapter;
import com.jayhalani.popularmovies.adapters.TrailerAdapter;
import com.jayhalani.popularmovies.database.FavDatabase;
import com.jayhalani.popularmovies.database.Favorite;
import com.jayhalani.popularmovies.databinding.ActivityDetailsBinding;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.model.reviews.Review;
import com.jayhalani.popularmovies.model.reviews.ReviewResult;
import com.jayhalani.popularmovies.model.trailers.Trailer;
import com.jayhalani.popularmovies.model.trailers.TrailerResult;
import com.jayhalani.popularmovies.utils.AppExecutors;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerOnClickListener {
    
    
    private static final String TAG = "DetailsActivity";
    
    ActivityDetailsBinding mBinding;
    
    TrailerAdapter trailerAdapter;
    MovieResults movieResult;
    Favorite favorite;
    Toast mToast;
    private int movieId;
    private String movieTitle;
    private String trailerUrl;
    private String trailerKey;
    FavDatabase mDatabase;
    private boolean isFavorite = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mDatabase = FavDatabase.getInstance(getApplicationContext());
        getDataFromIntent();
        handleCollapsingLayout();
        
        //Setup ToolBar
        setSupportActionBar(mBinding.detailsInfoAppbarLayout.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        // SetUp Trailer AndReview RecyclerView
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this);
        trailerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.detailsInfoTrailers.rvMovieTrailer.setLayoutManager(trailerLayoutManager);
        mBinding.detailsInfoTrailers.rvMovieTrailer.setHasFixedSize(true);
        
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);
        reviewLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mBinding.detailsInfoReviews.rvMovieReview.setLayoutManager(reviewLayoutManager);
        mBinding.detailsInfoReviews.rvMovieReview.setHasFixedSize(true);
        setupRecyclerViews();
        
    }
    
    // Retrieve data from Intent
    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String voteAverageString;
            String voteCountsString;
            if (intent.hasExtra(Constants.RESULT_DATA)) {
                movieResult =
                        (MovieResults) intent.getSerializableExtra(Constants.RESULT_DATA);
                favorite = new Favorite(movieResult.getId(), movieResult.getTitle(),
                        movieResult.getPosterPath(), movieResult.getBackdropPath(),
                        movieResult.getVoteAverage(), movieResult.getVoteCount(),
                        movieResult.getOverview(), movieResult.getReleaseDate());
                
                movieId = movieResult.getId();
                movieTitle = movieResult.getTitle();
                voteAverageString = movieResult.getVoteAverage() + "%";
                voteCountsString = movieResult.getVoteCount() + " votes";
                
                mBinding.detailsInfoTitle.tvDetailMovieTitle.setText(movieResult.getTitle());
                mBinding.detailsInfoTitle.tvDetailMovieReleaseDate.setText(movieResult.getReleaseDate());
                mBinding.detailsInfoTitle.tvDetailMovieVoteAverage.setText(voteAverageString);
                mBinding.detailsInfoTitle.tvDetailMovieVoteCounts.setText(voteCountsString);
                mBinding.detailsInfoSynopsis.tvDetailMovieSynopsisPlot.setText(movieResult.getOverview());
                
                Picasso.get().load(new NetworkUtils().builtImageUrl(movieResult.getBackdropPath(),
                        Constants.IMAGES_QUALITY_HIGH))
                        .placeholder(R.drawable.movie_backdrop_placeholder)
                        .into(mBinding.detailsInfoAppbarLayout.ivDetailMoviePoster);
                
                mBinding.detailsInfoAppbarLayout.ivDetailTrailerPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTrailer(trailerKey, trailerUrl);
                    }
                });
                
            } else if (intent.hasExtra(Constants.FAVORITE_DATA)) {
                favorite = (Favorite) intent.getSerializableExtra(Constants.FAVORITE_DATA);
                movieResult = new MovieResults(favorite.getId(), favorite.getTitle(),
                        favorite.getPosterPath(), favorite.getBackdropPath(),
                        favorite.getVoteAverage(), favorite.getVoteCount(),
                        favorite.getOverview(), favorite.getReleaseDate());
                movieId = favorite.getMovieId();
                movieTitle = favorite.getTitle();
                voteAverageString = favorite.getVoteAverage() + "%";
                voteCountsString = favorite.getVoteCount() + " votes";
                
                mBinding.detailsInfoTitle.tvDetailMovieTitle.setText(favorite.getTitle());
                mBinding.detailsInfoTitle.tvDetailMovieReleaseDate.setText(favorite.getReleaseDate());
                mBinding.detailsInfoTitle.tvDetailMovieVoteAverage.setText(voteAverageString);
                mBinding.detailsInfoTitle.tvDetailMovieVoteCounts.setText(voteCountsString);
                mBinding.detailsInfoSynopsis.tvDetailMovieSynopsisPlot.setText(favorite.getOverview());
                
                Picasso.get().load(new NetworkUtils().builtImageUrl(favorite.getBackdropPath(),
                        Constants.IMAGES_QUALITY_HIGH))
                        .placeholder(R.drawable.movie_backdrop_placeholder)
                        .into(mBinding.detailsInfoAppbarLayout.ivDetailMoviePoster);
                
                mBinding.detailsInfoAppbarLayout.ivDetailTrailerPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openTrailer(trailerKey, trailerUrl);
                    }
                });
            }
        }
    }
    
    
    // Handling Collapsing Layout
    private void handleCollapsingLayout() {
        
        mBinding.detailsInfoAppbarLayout.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (movieTitle != null && !movieTitle.isEmpty()) {
                        mBinding.detailsInfoAppbarLayout.collapsingToolbarLayout.setTitle(movieTitle);
                    } else {
                        mBinding.detailsInfoAppbarLayout.collapsingToolbarLayout.setTitle(getResources().getString(R.string.movie_details));
                    }
                    isShow = true;
                } else if (isShow) {
                    mBinding.detailsInfoAppbarLayout.collapsingToolbarLayout.setTitle(" "); //careful there should a space between
                    // double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }
    
    // Trailer OnClickListener
    @Override
    public void OnItemClickListener(TrailerResult trailerResult) {
        String key = trailerResult.getKey();
        String url = new NetworkUtils().buildYoutubeWebUrl(key);
        openTrailer(key, url);
    }
    
    // Open Trailer in web or Youtube App
    private void openTrailer(String key, String url) {
        if (new NetworkUtils().isNetworkConnected(this)) {
            Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + key));
            Intent youtubeWebIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            try {
                startActivity(youtubeAppIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(youtubeWebIntent);
            }
        } else {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(DetailsActivity.this,
                    getResources().getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
    
    // Retrieve key and generate Url for Trailer
    private void setupRecyclerViews() {
        
        // Set data to Trailer recyclerView
        StringRequest trailerRequest = new StringRequest(
                new NetworkUtils().buildUrlByMovieId(Constants.SORT_BY_VIDEOS, String.valueOf(movieId)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Trailer trailer = gson.fromJson(response, Trailer.class);
                        List<TrailerResult> trailerResults = trailer.getResults();
                        if (!trailerResults.isEmpty()) {
                            // Trailer RecyclerView
                            trailerAdapter = new TrailerAdapter(trailerResults, movieResult,
                                    DetailsActivity.this);
                            mBinding.detailsInfoTrailers.rvMovieTrailer.setAdapter(trailerAdapter);
                            trailerKey = trailerResults.get(0).getKey();
                            trailerUrl = new NetworkUtils().buildYoutubeWebUrl(trailerKey);
                        } else {
                            mBinding.detailsInfoTrailers.tvMovieTrailerTitle
                                    .setText(getResources().getString(R.string.no_trailers_available));
                            mBinding.detailsInfoAppbarLayout.ivDetailTrailerPlay.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        RequestQueue trailerRequestQueue = Volley.newRequestQueue(this);
        trailerRequestQueue.add(trailerRequest);
        
        // Set data to Review recyclerView
        StringRequest reviewsRequest = new StringRequest(
                new NetworkUtils().buildUrlByMovieId(Constants.SORT_BY_REVIEWS, String.valueOf(movieId)),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Review review = gson.fromJson(response, Review.class);
                        List<ReviewResult> reviewResults = review.getResults();
                        if (!reviewResults.isEmpty()) {
                            // Reviews RecyclerView
                            ReviewAdapter adapter = new ReviewAdapter(reviewResults);
                            mBinding.detailsInfoReviews.rvMovieReview.setAdapter(adapter);
                        } else {
                            mBinding.detailsInfoReviews.tvMovieReviewTitle
                                    .setText(getResources().getString(R.string.no_reviews_available));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.toString());
            }
        });
        
        RequestQueue reviewsRequestQueue = Volley.newRequestQueue(this);
        reviewsRequestQueue.add(reviewsRequest);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.details_favorite) {
            if (isFavorite) {
                isFavorite = false;
                item.setIcon(R.drawable.ic_favorite_border_white_24dp);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mDatabase.favoriteDao().hasMovieId(movieId)) {
                            mDatabase.favoriteDao().deleteMovieById(movieId);
                        }
                    }
                });
            } else {
                isFavorite = true;
                item.setIcon(R.drawable.ic_favorite_24dp);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!mDatabase.favoriteDao().hasMovieId(movieId)) {
                            mDatabase.favoriteDao().insertMovie(favorite);
                        }
                    }
                });
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mDatabase.favoriteDao().hasMovieId(movieId)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isFavorite = true;
                            menu.getItem(0).setIcon(R.drawable.ic_favorite_24dp);
                        }
                    });
                }
            }
        });
        return true;
    }
    
}

