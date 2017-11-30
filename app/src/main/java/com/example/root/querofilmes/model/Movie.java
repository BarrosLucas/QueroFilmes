package com.example.root.querofilmes.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by root on 28/11/17.
 */
@Entity(tableName = "Movie")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int idMovie;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "released")
    private String released;

    @ColumnInfo(name = "runtime")
    private String runtime;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "director")
    private String director;

    @ColumnInfo(name = "plot")
    private String plot;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "production")
    private String production;

    public Movie(String title, String year, String released, String runtime, String genre, String director, String plot, String language, String poster, String production){
        setTitle(title);
        setYear(year);
        setReleased(released);
        setRuntime(runtime);
        setGenre(genre);
        setDirector(director);
        setPlot(plot);
        setLanguage(language);
        setPoster(poster);
        setProduction(production);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public int getIdMovie(){
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

}
