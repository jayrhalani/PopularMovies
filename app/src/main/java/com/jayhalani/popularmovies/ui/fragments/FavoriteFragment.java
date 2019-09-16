package com.jayhalani.popularmovies.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.adapters.FavoriteAdapter;
import com.jayhalani.popularmovies.database.FavDatabase;
import com.jayhalani.popularmovies.database.Favorite;
import com.jayhalani.popularmovies.databinding.FragmentFavoriteBinding;
import com.jayhalani.popularmovies.ui.activities.DetailsActivity;
import com.jayhalani.popularmovies.utils.Constants;
import com.jayhalani.popularmovies.viewmodels.MainViewModel;

import java.util.List;

public class FavoriteFragment extends Fragment
        implements FavoriteAdapter.FavoriteAdapterOnClickHandler {
    private FragmentFavoriteBinding mBinding;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteAdapter.FavoriteAdapterOnClickHandler onClickHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        onClickHandler = this;
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.number_of_columns));
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mBinding.rvFavMovieList.setLayoutManager(layoutManager);
        mBinding.rvFavMovieList.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(getActivity(), onClickHandler);
        mBinding.rvFavMovieList.setAdapter(favoriteAdapter);
        FavDatabase mDatabase = FavDatabase.getInstance(getContext());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorites().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                favoriteAdapter.setFavorites(favorites);
            }
        });
    }

    @Override
    public void OnItemClickListener(Favorite favorite) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(Constants.FAVORITE_DATA, favorite);
        startActivity(intent);
    }
}
