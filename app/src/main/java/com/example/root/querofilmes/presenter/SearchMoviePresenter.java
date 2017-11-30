package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.model.service.MovieResponse;
import com.example.root.querofilmes.view.SearchMovie;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.querofilmes.model.service.MovieInterface.charset;

/**
 * Created by root on 30/11/17.
 */

public class SearchMoviePresenter {
    private static Context searchMovieContext;
    private static SearchMovie searchMovie;

    public SearchMoviePresenter(Context context, SearchMovie searchMovie){
        searchMovieContext = context;
        this.searchMovie = searchMovie;
    }

    public void searchMovieOnOMDB(String title) throws UnsupportedEncodingException {
        String query = String.format("t=%s&type=%s&apikey=%s",
                URLEncoder.encode(title, charset),
                URLEncoder.encode(MovieInterface.TYPE, charset),
                URLEncoder.encode(MovieInterface.API_KEY, charset));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MovieInterface.MOVIE_API_BASE_URL+"?"+query)
                .build();
        Log.i("URL",MovieInterface.MOVIE_API_BASE_URL+"?"+query);

        MovieInterface movieInterface = retrofit.create(MovieInterface.class);
        Call<MovieResponse> callMovie = movieInterface.search();
        callMovie.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(searchMovieContext, "Conexão estabelecida", Toast.LENGTH_SHORT).show();
                    if(response.body() != null){
                        Toast.makeText(searchMovieContext,"null",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(searchMovieContext,"not null",Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(searchMovieContext,"Falha",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
