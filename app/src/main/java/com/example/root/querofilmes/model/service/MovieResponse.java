package com.example.root.querofilmes.model.service;

/**
 * Created by root on 30/11/17.
 */

public class MovieResponse {
    public String title;
    public String year;
    public String released;
    public String runtime;
    public String genre;
    public String director;
    public String plot;
    public String language;
    public String poster;
    public String production;

    public MovieResponse(String title, String year, String released, String runtime, String genre, String director, String plot, String language, String poster, String production) {
        this.title = title;
        this.year = year;
        this.released = released;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
        this.plot = plot;
        this.language = language;
        this.poster = poster;
        this.production = production;
    }



    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleased() {
        return this.released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRuntime() {
        return this.runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return this.plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


}
