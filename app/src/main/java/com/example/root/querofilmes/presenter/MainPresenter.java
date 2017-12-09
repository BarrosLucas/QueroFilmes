package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.querofilmes.InitialScreem;
import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Database;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.view.AdapterMovieMainView;
import com.example.root.querofilmes.view.AdapterMovieView;
import com.example.root.querofilmes.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.querofilmes.model.service.MovieInterface.charset;


/**
 * Created by root on 28/11/17.
 */

public class MainPresenter {
    private static Context mainActivityContext;
    private static InitialScreem mainActivity;
    private static List<Movie> movies;
    private static ListMovieResponse listMovie;

    public static List<Movie> moviesOrganized;

    public MainPresenter(Context context, InitialScreem mainActivity){
        mainActivityContext = context;
        this.mainActivity = mainActivity;
    }

    public static void updateListMovies(){
        int countMovies = Database.countMovie(AppDatabase.getAppDatabase(mainActivityContext));
        movies = new ArrayList<>();
        if(countMovies>0){
            movies.addAll(Database.getMovies(AppDatabase.getAppDatabase(mainActivityContext)));
            Log.i("Size",""+movies.size());
        }
        mainActivity.listView.setAdapter(null);
        mainActivity.listView.setAdapter(new AdapterMovieMainView(mainActivityContext,generateNewList(movies)));

    }

    private void updateListSearchMovies(ListMovieResponse listMovieResponses){
        mainActivity.isSearch = false;
        setIsSearch();
        if(mainActivity.isSearch) {
            mainActivity.listView.setVisibility(View.GONE);
            mainActivity.searchListView.setVisibility(View.VISIBLE);

            mainActivity.searchListView.setAdapter(null);
            mainActivity.searchListView.setAdapter(new AdapterMovieView(mainActivityContext, listMovie));
        }
    }


    public static List<Movie> generateNewList(List<Movie> movies){
        List<Movie> newListMovies = new ArrayList<>();
        for(Movie movie: movies){
            if(movie.getFavorite()){
                newListMovies.add(movie);
            }
        }
        for (Movie movie: movies){
            if(movie.getFavorite()==false){
                newListMovies.add(movie);
            }
        }
        moviesOrganized = newListMovies;
        return newListMovies;
    }

    public void populateCards(){
        mainActivity.isSearch = true;
        setIsSearch();
        updateListMovies();
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
                        updateListSearchMovies(listMovie);
                    }else{
                        Toast.makeText(mainActivityContext,"null",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    ResponseBody responseBody = response.errorBody();
                    try {
                        Log.i("Erro",responseBody.source().readUtf8());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(mainActivityContext,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(mainActivityContext, "Vish", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListMovieResponse> call, Throwable t) {
                Toast.makeText(mainActivityContext,"Falha",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public static void populateFavoritesMovies(){
        mainActivity.isSearch = true;
        setIsSearch();
        int countMovies = Database.countMovie(AppDatabase.getAppDatabase(mainActivityContext));
        movies = new ArrayList<>();
        if(countMovies>0){
            movies.addAll(Database.getMovies(AppDatabase.getAppDatabase(mainActivityContext)));
            Log.i("Size",""+movies.size());
        }
        mainActivity.listView.setAdapter(null);
        mainActivity.listView.setAdapter(new AdapterMovieMainView(mainActivityContext,generateFavoritList(movies)));
    }

    public static List<Movie> generateFavoritList(List<Movie> movies){
        List<Movie> theReturn = new ArrayList<>();
        for(Movie movie: movies){
            if(movie.getFavorite()){
                theReturn.add(movie);
            }
        }
        return theReturn;
    }

    public static void setIsSearch(){
        mainActivity.isSearch = !mainActivity.isSearch;
        if(mainActivity.isSearch){
            mainActivity.listView.setVisibility(View.GONE);
            mainActivity.searchListView.setVisibility(View.VISIBLE);
        }else{
            mainActivity.listView.setVisibility(View.VISIBLE);
            mainActivity.searchListView.setVisibility(View.GONE);
            updateListMovies();
        }
    }

    public String getIdOmdbMovie(int position){
        return listMovie.Search.get(position).getImdbID();
    }
}
