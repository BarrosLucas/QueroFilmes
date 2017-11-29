package com.example.root.querofilmes.model.DAO;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.root.querofilmes.model.Movie;

/**
 * Created by root on 28/11/17.
 */

public class Database {

        private static final String TAG = Database.class.getName();

        public static void populateAsync(@NonNull final AppDatabase db, Movie movie) {
            PopulateDbAsync task = new PopulateDbAsync(db,movie);
            task.execute();
        }

        private static Movie addMovie(final AppDatabase db, Movie movie) {
            db.movieDao().insertAll(movie);
            return movie;
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
