package com.jayhalani.popularmovies.ui.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.adapters.MovieAdapter;
import com.jayhalani.popularmovies.databinding.FragmentTopRatedBinding;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.ui.activities.DetailsActivity;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.jayhalani.popularmovies.viewmodels.SortByViewModel;

import java.util.List;

public class TopRatedFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler {
    public static final String MOVIE_RESULT_STATE = "movie_result_state";
    FragmentTopRatedBinding mBinding;
    MovieAdapter.MovieAdapterOnClickHandler onClickHandler;
    MovieAdapter movieAdapter;
    private Toast mToast;
    private GridLayoutManager layoutManager;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_rated, container, false);
        layoutManager = new GridLayoutManager(getContext(),
                getResources().getInteger(R.integer.number_of_columns));
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mBinding.rvTopMovieList.setLayoutManager(layoutManager);
        mBinding.rvTopMovieList.setHasFixedSize(true);
        onClickHandler = this;
        return mBinding.getRoot();
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieAdapter = new MovieAdapter(this);
    
        SortByViewModel sortByViewModel = ViewModelProviders
                .of(this).get(SortByViewModel.class);
        sortByViewModel.getMovieResults().observe(this, new Observer<List<MovieResults>>() {
            @Override
            public void onChanged(@Nullable List<MovieResults> movieResults) {
                movieAdapter.setMovieResults(movieResults);
                mBinding.rvTopMovieList.setAdapter(movieAdapter);
            }
        });
    
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_RESULT_STATE)) {
            layoutManager.onRestoreInstanceState(savedInstanceState
                    .getParcelable(MOVIE_RESULT_STATE));
        }
        
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_RESULT_STATE, layoutManager.onSaveInstanceState());
    }
    
    @Override
    public void OnItemClickListener(MovieResults movieResults) {
        if (new NetworkUtils().isNetworkConnected(getActivity())) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("RESULT_DATA", movieResults);
            startActivity(intent);
        } else {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "No Connection",
                    Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
