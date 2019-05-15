package com.jayhalani.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.model.reviews.ReviewResult;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    
    private List<ReviewResult> reviewResults;
    
    public ReviewAdapter(List<ReviewResult> reviewResults) {
        this.reviewResults = reviewResults;
    }
    
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_movie_review, parent, false);
        return new ReviewViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewResult reviewResult = reviewResults.get(position);
        holder.tvReviewAuthor.setText(reviewResult.getAuthor());
        holder.tvReviewContent.setText(reviewResult.getContent());
    }
    
    @Override
    public int getItemCount() {
        return reviewResults.size();
    }
    
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewAuthor;
        TextView tvReviewContent;
        
        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewAuthor = itemView.findViewById(R.id.tv_movie_review_item_author);
            tvReviewContent = itemView.findViewById(R.id.tv_movie_review_item_content);
        }
    }
}
