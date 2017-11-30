package com.example.root.querofilmes.model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.root.querofilmes.model.Movie;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */
@Dao
public interface MovieDAO {
    @Query("SELECT * FROM Movie")
    List<Movie> getAll();

    @Query("SELECT * FROM Movie where title LIKE  :title")
    Movie findByTitle(String title);

    @Query("SELECT COUNT(*) from Movie")
    int countMovie();

    @Insert
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}
