package com.thebaileybrew.finalflix.database;

import android.app.Application;

import com.thebaileybrew.finalflix.models.Credit;
import com.thebaileybrew.finalflix.models.Film;
import com.thebaileybrew.finalflix.models.Movie;
import com.thebaileybrew.finalflix.models.Review;
import com.thebaileybrew.finalflix.models.Videos;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private String mSort;
    private String mLanguage;
    private String mYear;
    private String mSearch;

    private LiveData<List<Movie>> mAllMovies;
    private LiveData<List<Movie>> mFavoritesOnly;


    private final MutableLiveData<List<Movie>> mAllMoviesMutable = new MutableLiveData<>();
    private final MutableLiveData<Integer> mFavoritesMutable = new MutableLiveData<>();

    public MovieViewModel (Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = Transformations.switchMap(mAllMoviesMutable, new Function<List<Movie>, LiveData<List<Movie>>>() {
            @Override
            public LiveData<List<Movie>> apply(List<Movie> input) {
                return mRepository.loadMovies();
            }
        });
        mFavoritesOnly = Transformations.switchMap(mFavoritesMutable, new Function<Integer, LiveData<List<Movie>>>() {
            @Override
            public LiveData<List<Movie>> apply(Integer input) {
                return mRepository.getFavorites(input);
            }
        });

    }

    public LiveData<List<Movie>> getAllMovies(String sort, String language, String year, String search) {
        mSort = sort;
        mSearch = search;
        mLanguage = language;
        mYear = year;
        mAllMovies = mRepository.getAllMovies(sort, language, year, search);
        return mAllMovies;
    }

    public LiveData<List<Movie>> loadMovies() {
        return mAllMovies;
    }

    public LiveData<List<Movie>> loadFavorites() {
        return mFavoritesOnly;
    }

    public LiveData<List<Movie>> getSingleMovie(int movieID) {
        mAllMovies = mRepository.getSingleMovie(movieID);
        return mAllMovies;
    }


    public void insert(Movie movie) {
        mRepository.insertMovie(movie);
    }

}
