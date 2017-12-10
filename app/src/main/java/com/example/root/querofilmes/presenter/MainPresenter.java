package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.root.querofilmes.view.activity.InitialScreem;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Database;
import com.example.root.querofilmes.model.DAO.Movie;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.view.adapter.AdapterMovieMainView;
import com.example.root.querofilmes.view.adapter.AdapterMovieView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public static void updateListMovies() throws UnsupportedEncodingException {
        int countMovies = Database.countMovie(AppDatabase.getAppDatabase(mainActivityContext));
        movies = new ArrayList<>();
        if(countMovies>0){
            movies.addAll(Database.getMovies(AppDatabase.getAppDatabase(mainActivityContext)));
            Log.i("Size",""+movies.size());
        }else{
            //Always that the database not contaim none movie, suggestions are show to user
            searchMovieOnOMDB("final destination");
        }
        mainActivity.listView.setAdapter(null);
        mainActivity.listView.setAdapter(new AdapterMovieMainView(mainActivityContext,generateNewList(movies)));

    }

    private static void updateListSearchMovies(ListMovieResponse listMovieResponses) throws UnsupportedEncodingException {
        //Show the list of movies found in API
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
        //Organize the movies to show first the favorites
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

    public void populateCards() throws UnsupportedEncodingException {
        mainActivity.isSearch = true;
        setIsSearch();
        updateListMovies();
    }

    //Search movies with title informed from user
    public static void searchMovieOnOMDB(String title) throws UnsupportedEncodingException {
        String query = String.format("?s=%s&type=%s&apikey=%s",
                URLEncoder.encode(title, charset),
                URLEncoder.encode(MovieInterface.TYPE, charset),
                URLEncoder.encode(MovieInterface.API_KEY, charset));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MovieInterface.MOVIE_API_BASE_URL+query)
                .build();

        Log.i("Query",query);

        MovieInterface movieInterface = retrofit.create(MovieInterface.class);
        rx.Observable<ListMovieResponse> observable = movieInterface.searchMovies();

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListMovieResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mainActivityContext,"Ocorreu alguma falha",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ListMovieResponse listMovieResponse) {
                try {
                    listMovie = listMovieResponse;
                    updateListSearchMovies(listMovie);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //Show only favorite movies
    public static void populateFavoritesMovies() throws UnsupportedEncodingException {
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

    //Generate list that contaim only the favorite movies
    public static List<Movie> generateFavoritList(List<Movie> movies){
        List<Movie> theReturn = new ArrayList<>();
        for(Movie movie: movies){
            if(movie.getFavorite()){
                theReturn.add(movie);
            }
        }
        return theReturn;
    }

    //Set the view to show suggestion or results search
    public static void setIsSearch() throws UnsupportedEncodingException {
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
