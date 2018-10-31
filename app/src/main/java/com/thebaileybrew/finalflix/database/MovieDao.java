package com.thebaileybrew.finalflix.database;

import com.thebaileybrew.finalflix.models.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM allmovies ORDER BY movie_id")
    LiveData<List<Movie>> loadMovies();

    @Query("SELECT * FROM allmovies WHERE movie_id = :movieID")
    LiveData<List<Movie>> loadSingleMovie(int movieID);

    @Query("SELECT * FROM allmovies WHERE favorite = :value")
    LiveData<List<Movie>> getFavorites(int value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);
}
