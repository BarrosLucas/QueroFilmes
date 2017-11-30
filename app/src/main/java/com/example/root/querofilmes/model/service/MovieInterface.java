package com.example.root.querofilmes.model.service;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by root on 30/11/17.
 */

public interface MovieInterface {
    String MOVIE_API_BASE_URL = "http://www.omdbapi.com/";
    String API_KEY = "PlsBanMe2";
    String charset = "UTF-8";
    String TYPE = "movie";


    @GET("search")
    Call<MovieResponse> search();
}
