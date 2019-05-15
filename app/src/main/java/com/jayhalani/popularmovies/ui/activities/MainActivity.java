package com.jayhalani.popularmovies.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jayhalani.popularmovies.R;
import com.jayhalani.popularmovies.databinding.ActivityMainBinding;
import com.jayhalani.popularmovies.ui.fragments.FavoriteFragment;
import com.jayhalani.popularmovies.ui.fragments.PopularFragment;
import com.jayhalani.popularmovies.ui.fragments.TopRatedFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    
    ActivityMainBinding mBinding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.bottomNavView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            loadFragment(new PopularFragment());
        }
    }
    
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("HANDLE_ROTATION", "handle_rotation");
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_popular:
                return loadFragment(new PopularFragment());
            
            case R.id.navigation_top_rated:
                return loadFragment(new TopRatedFragment());
            
            case R.id.navigation_favorite:
                return loadFragment(new FavoriteFragment());
        }
        return false;
    }
}
