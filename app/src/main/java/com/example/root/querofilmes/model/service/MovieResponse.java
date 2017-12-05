package com.example.root.querofilmes.model.service;

/**
 * Created by root on 30/11/17.
 */

public class MovieResponse {
    public String Title;
    public String Year;
    public String Released;
    public String Runtime;
    public String Genre;
    public String Director;
    public String Plot;
    public String Language;
    public String Poster;
    public String Production;

    public MovieResponse(String title, String year, String released, String runtime, String genre, String director, String plot, String language, String poster, String production) {
        this.Title = title;
        this.Year = year;
        this.Released = released;
        this.Runtime = runtime;
        this.Genre = genre;
        this.Director = director;
        this.Plot = plot;
        this.Language = language;
        this.Poster = poster;
        this.Production = production;
    }



    public String getProduction() {
        return Production;
    }

    public void setProduction(String production) {
        this.Production = production;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getYear() {
        return this.Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }

    public String getReleased() {
        return this.Released;
    }

    public void setReleased(String released) {
        this.Released = released;
    }

    public String getRuntime() {
        return this.Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getGenre() {
        return this.Genre;
    }

    public void setGenre(String genre) {
        this.Genre = genre;
    }

    public String getDirector() {
        return this.Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    public String getPlot() {
        return this.Plot;
    }

    public void setPlot(String plot) {
        this.Plot = plot;
    }

    public String getLanguage() {
        return this.Language;
    }

    public void setLanguage(String language) {
        this.Language = language;
    }

    public String getPoster() {
        return this.Poster;
    }

    public void setPoster(String poster) {
        this.Poster = poster;
    }


}
