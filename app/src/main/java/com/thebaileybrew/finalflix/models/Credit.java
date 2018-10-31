package com.thebaileybrew.finalflix.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Credit {

    private int creditID;
    private int movieID;
    private  String creditCharacterName;
    private String creditActorName;
    private String creditPath;

    public int getCreditID() {
        return creditID;
    }

    public void setCreditID(int creditID) {
        this.creditID = creditID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getCreditCharacterName() {
        return creditCharacterName;
    }

    public void setCreditCharacterName(String creditCharacterName) {
        this.creditCharacterName = creditCharacterName;
    }

    public String getCreditActorName() {
        return creditActorName;
    }

    public void setCreditActorName(String creditActorName) {
        this.creditActorName = creditActorName;
    }

    public String getCreditPath() {
        return creditPath;
    }

    public void setCreditPath(String creditPath) {
        this.creditPath = creditPath;
    }
}
