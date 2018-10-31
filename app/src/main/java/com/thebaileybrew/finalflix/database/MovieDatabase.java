package com.thebaileybrew.finalflix.database;

import android.content.Context;
import android.os.AsyncTask;

import com.thebaileybrew.finalflix.models.Credit;
import com.thebaileybrew.finalflix.models.Film;
import com.thebaileybrew.finalflix.models.Movie;
import com.thebaileybrew.finalflix.models.Review;
import com.thebaileybrew.finalflix.models.Videos;
import com.thebaileybrew.finalflix.utils.jsonUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MovieDatabase INSTANCE;
    private static String DATABASE_NAME = "flixies";

    static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
