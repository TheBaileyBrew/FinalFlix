package com.thebaileybrew.finalflix.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Film {

    private int movieID;
    private String movieTagLine;
    private int movieRuntime;
    private String movieGenre;

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTagLine() {
        return movieTagLine;
    }

    public void setMovieTagLine(String movieTagLine) {
        this.movieTagLine = movieTagLine;
    }

    public int getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(int movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }
}

