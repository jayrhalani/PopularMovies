package com.jayhalani.popularmovies.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.adapters.MovieAdapter;
import com.jayhalani.popularmovies.databinding.FragmentTopRatedBinding;
import com.jayhalani.popularmovies.model.movies.MovieResults;
import com.jayhalani.popularmovies.ui.activities.DetailsActivity;
import com.jayhalani.popularmovies.utils.NetworkUtils;
import com.jayhalani.popularmovies.viewmodels.SortByViewModel;

import java.util.List;
import java.util.Objects;

public class TopRatedFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String MOVIE_RESULT_STATE = "movie_result_state";
    private FragmentTopRatedBinding mBinding;
    private MovieAdapter.MovieAdapterOnClickHandler onClickHandler;
    private MovieAdapter movieAdapter;
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
        if (new NetworkUtils().isNetworkConnected(Objects.requireNonNull(getActivity()))) {
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
