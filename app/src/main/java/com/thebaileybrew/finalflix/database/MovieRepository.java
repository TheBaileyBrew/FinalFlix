package com.thebaileybrew.finalflix.database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.thebaileybrew.finalflix.BuildConfig;
import com.thebaileybrew.finalflix.models.Credit;
import com.thebaileybrew.finalflix.models.Film;
import com.thebaileybrew.finalflix.models.Movie;
import com.thebaileybrew.finalflix.models.Review;
import com.thebaileybrew.finalflix.models.Videos;
import com.thebaileybrew.finalflix.utils.UrlUtils;
import com.thebaileybrew.finalflix.utils.jsonUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MovieRepository {
    private static final String TAG = MovieRepository.class.getSimpleName();

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    public MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
    }
    public LiveData<List<Movie>> loadMovies() {
        mAllMovies = mMovieDao.loadMovies();
        return mAllMovies;
    }

    public LiveData<List<Movie>> getFavorites(int i) {
        mAllMovies = mMovieDao.getFavorites(i);
        return mAllMovies;
    }

    public LiveData<List<Movie>> getAllMovies(String sorting, String language, String year, String search) {
        populateDBAsyncTask populate = new populateDBAsyncTask();
        LiveData<List<Movie>> movies = null;
        try {
            movies = populate.execute(sorting, language, year, search).get();
        } catch (ExecutionException e) {
            Log.e(TAG, "getAllMovies: ", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return movies;
    }
    LiveData<List<Movie>> getSingleMovie(int movieID) {
        mAllMovies = mMovieDao.loadSingleMovie(movieID);
        return mAllMovies;
    } //Returns ONE Movie in query
    public void insertMovie(Movie movie) {
        new insertMovieAsyncTask(mMovieDao).execute(movie);
    }

    private static class insertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao mAsyncDao;

        insertMovieAsyncTask(MovieDao movieDao) {
            mAsyncDao = movieDao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncDao.insertMovie(params[0]);
            return null;
        }
    }

    private static class populateDBAsyncTask extends AsyncTask<String, Void, LiveData<List<Movie>>> {

        populateDBAsyncTask() {}

        @Override
        protected LiveData<List<Movie>> doInBackground(String...strings) {
            if (strings.length < 3 || strings[0] == null) {
                return null;
            }
            String sortingOrder = strings[0];
            String languageFilter = strings[1];
            Log.e(TAG, "doInBackground: language" + languageFilter);
            String filterYear = strings[2];
            Log.e(TAG, "doInBackground: year" + filterYear);
            String searchQuery = strings[3];
            Log.e(TAG, "doInBackground: searching" + searchQuery);

            URL movieRequestUrl = UrlUtils.buildMovieUrl(BuildConfig.API_KEY,
                    languageFilter, sortingOrder, filterYear, searchQuery);
            try {
                String jsonMovieResponse = jsonUtils.makeHttpsRequest(movieRequestUrl);
                jsonUtils.extractMoviesFromJson(jsonMovieResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LiveData<List<Movie>> movies) {
            super.onPostExecute(movies);
        }
    }
}
