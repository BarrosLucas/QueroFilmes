package com.example.root.querofilmes.model.DAO;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.root.querofilmes.model.Movie;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class Database {

        private static final String TAG = Database.class.getName();

        public static void populateAsync(@NonNull final AppDatabase db, Movie movie) {
            PopulateDbAsync task = new PopulateDbAsync(db,movie);
            task.execute();
        }

        public static List<Movie> getMovies(@NonNull final AppDatabase db){
            return db.movieDao().getAll();
        }

        private static Movie addMovie(final AppDatabase db, Movie movie) {
            db.movieDao().insertAll(movie);
            return movie;
        }

        public static int countMovie(@NonNull final AppDatabase db){
            return db.movieDao().countMovie();
        }

        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

            private final AppDatabase mDb;
            private final Movie movie;

            PopulateDbAsync(AppDatabase db,Movie movie) {
                mDb = db;
                this.movie = movie;
            }

            @Override
            protected Void doInBackground(final Void... params) {
                addMovie(mDb,movie);
                return null;
            }
        }

}
