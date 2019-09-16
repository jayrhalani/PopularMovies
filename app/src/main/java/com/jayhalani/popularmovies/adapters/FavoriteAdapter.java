package com.jayhalani.popularmovies.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.database.Favorite;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favorites;
    private Context context;
    private FavoriteAdapterOnClickHandler onClickHandler;

    public FavoriteAdapter(Context context,
                           FavoriteAdapterOnClickHandler onClickHandler) {
        this.context = context;
        this.onClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_movie_thumbnail, parent, false);
        return new FavoriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);
        if (new NetworkUtils().isNetworkConnected(context)) {
            Picasso.get().load(new NetworkUtils().builtImageUrl(favorite.getPosterPath(),
                    Constants.IMAGES_QUALITY_SMALL)).into(holder.ivMovieThumbnail);
        } else {
            Picasso.get().load(R.drawable.movie_thumbnail_placeholder).into(holder.ivMovieThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (favorites == null) {
            return 0;
        }
        return favorites.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivMovieThumbnail;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivMovieThumbnail = itemView.findViewById(R.id.iv_movie_list_thumbnail);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            onClickHandler.OnItemClickListener(favorites.get(adapterPosition));
        }
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    // ItemOnClickListener Interface
    public interface FavoriteAdapterOnClickHandler {
        void OnItemClickListener(Favorite favorite);
    }
}
