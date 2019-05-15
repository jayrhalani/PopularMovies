package com.jayhalani.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.model.trailers.TrailerResult;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    
    private final List<TrailerResult> movieTrailers;
    private final MovieResults movieResults;
    private final TrailerOnClickListener onClickHandler;
    
    public interface TrailerOnClickListener {
        void OnItemClickListener(TrailerResult trailerResult);
    }
    
    public TrailerAdapter(List<TrailerResult> movieTrailers, MovieResults movieResults, TrailerOnClickListener onClickHandler) {
        this.movieTrailers = movieTrailers;
        this.movieResults = movieResults;
        this.onClickHandler = onClickHandler;
    }
    
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_movie_trailer, parent, false);
        return new TrailerViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Picasso.get().load(new NetworkUtils().builtImageUrl(movieResults.getPosterPath(), Constants.IMAGES_QUALITY_SMALL))
                .into(holder.ivMovieThumbnail);
    }
    
    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }
    
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        ImageView ivMovieThumbnail;
        
        private TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovieThumbnail = itemView.findViewById(R.id.iv_movie_trailer_list_thumbnail);
            itemView.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onClickHandler.OnItemClickListener(movieTrailers.get(adapterPosition));
        }
    }
}