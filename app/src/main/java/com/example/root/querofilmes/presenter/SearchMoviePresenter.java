package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieForList;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.model.service.MovieResponse;
import com.example.root.querofilmes.view.AdapterMovieView;
import com.example.root.querofilmes.view.SearchMovie;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

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

public class SearchMoviePresenter{
    private static Context searchMovieContext;
    private static SearchMovie searchMovie;
    public static ListMovieResponse listMovie;

    public SearchMoviePresenter(Context context, SearchMovie searchMovie){
        searchMovieContext = context;
        this.searchMovie = searchMovie;
    }

    public void searchMovieOnOMDB(String title) throws UnsupportedEncodingException {
        String query = String.format("?s=%s&type=%s&apikey=%s",
                URLEncoder.encode(title, charset),
                URLEncoder.encode(MovieInterface.TYPE, charset),
                URLEncoder.encode(MovieInterface.API_KEY, charset));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MovieInterface.MOVIE_API_BASE_URL+query)
                .build();

        Log.i("Query",query);

        MovieInterface movieInterface = retrofit.create(MovieInterface.class);
        Call<ListMovieResponse> callMovie = movieInterface.searchMovies();
        callMovie.enqueue(new Callback<ListMovieResponse>() {
            @Override
            public void onResponse(Call<ListMovieResponse> call, Response<ListMovieResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null){
                        listMovie = response.body();
                        searchMovie.listView.setAdapter(null);
                        searchMovie.listView.setAdapter(new AdapterMovieView(searchMovieContext,listMovie));
                    }else{
                        Toast.makeText(searchMovieContext,"null",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    ResponseBody responseBody = response.errorBody();
                    try {
                        Log.i("Erro",responseBody.source().readUtf8());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(searchMovieContext,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(searchMovieContext, "Vish", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                Toast.makeText(searchMovieContext,"Falha",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public String getIdOmdbMovie(int position){
        return listMovie.Search.get(position).getImdbID();
    }


}
