package com.thebaileybrew.finalflix.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.thebaileybrew.finalflix.FlixApplication;
import com.thebaileybrew.finalflix.R;
import com.thebaileybrew.finalflix.adapters.MovieAdapter;
import com.thebaileybrew.finalflix.database.MovieViewModel;
import com.thebaileybrew.finalflix.models.Movie;
import com.thebaileybrew.finalflix.utils.displayMetricsUtils;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MovieActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterClickHandler {

    private MovieViewModel movieViewModel;
    private SharedPreferences sharedPrefs;
    private String sorting, language, year, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        int columnIndex = displayMetricsUtils.calculateGridColumn(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columnIndex);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        final MovieAdapter adapter = new MovieAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        getSharedPreferences();

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        movieViewModel.loadMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getSharedPreferences() {
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(FlixApplication.getContext());
        //Get the sorting method from shared prefs
        String sortingKey = getString(R.string.preference_sort_key);
        String sortingDefault = getString(R.string.preference_sort_popular);
        sorting = sharedPrefs.getString(sortingKey, sortingDefault);
        //Get the langauge default from shared prefs
        String languageKey = getString(R.string.preference_sort_language_key);
        String languageDefault = getString(R.string.preference_sort_language_all);
        language = sharedPrefs.getString(languageKey, languageDefault);
        //Get filter year from shared prefs
        String filterYearKey = getString(R.string.preference_year_key);
        String filterYearDefault = getString(R.string.preference_year_default);
        year = sharedPrefs.getString(filterYearKey, filterYearDefault);
    }

    @Override
    public void onClick(View view, Movie movie) {

    }

    @Override
    public void onLongClick(View view, Movie movie, ImageView hiddenStar) {

    }
}
