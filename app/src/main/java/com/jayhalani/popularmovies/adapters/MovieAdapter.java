package com.jayhalani.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    
    private List<MovieResults> movieResults;
    private final MovieAdapterOnClickHandler onClickHandler;
    
    public MovieAdapter(MovieAdapterOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }
    
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_movie_thumbnail, parent, false);
        return new MovieViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieResults movieResults = this.movieResults.get(position);
        Picasso.get().load(new NetworkUtils()
                .builtImageUrl(movieResults.getPosterPath(), Constants.IMAGES_QUALITY_SMALL))
                .into(holder.ivThumbnail);
    }
    
    @Override
    public int getItemCount() {
        return movieResults.size();
    }
    
    public void setMovieResults(List<MovieResults> movieResults) {
        this.movieResults = movieResults;
    }
    
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        ImageView ivThumbnail;
        
        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.iv_movie_list_thumbnail);
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onClickHandler.OnItemClickListener(movieResults.get(adapterPosition));
        }
    }
    
    // ItemOnClickListener Interface
    public interface MovieAdapterOnClickHandler {
        void OnItemClickListener(MovieResults movieResults);
    }
}
