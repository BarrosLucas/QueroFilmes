package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Database;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.view.AdapterMovieMainView;
import com.example.root.querofilmes.view.MainActivity;
import com.example.root.querofilmes.view.MovieActivity;
import com.example.root.querofilmes.view.SearchMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 08/12/17.
 */

public class MoviePresenter {
    public static Context context;
    public MoviePresenter(final Context context, final int index, final MovieActivity movieActivity){
        this.context = context;

        final List<Movie> movies = MainPresenter.moviesOrganized;
        if(movies != null) {
            if(movies.size()>0) {
                MovieActivity.title.setText(movies.get(index).getTitle());
                MovieActivity.year.setText("Ano: "+movies.get(index).getYear());
                MovieActivity.language.setText("Idioma: "+movies.get(index).getLanguage());
                MovieActivity.production.setText("Produção: "+movies.get(index).getProduction());
                MovieActivity.genre.setText("Gênero: "+movies.get(index).getGenre());
                MovieActivity.plot.setText("Sinopse: "+movies.get(index).getPlot());
                Picasso.with(context).load(movies.get(index).getPoster()).into(MovieActivity.picture);
                MovieActivity.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle()));

                        MainPresenter.updateListMovies();

                        movieActivity.end();
                    }


                });

                if(movies.get(index).getFavorite()){
                    MovieActivity.favorite.setImageResource(R.drawable.star32px);
                }else{
                    MovieActivity.favorite.setImageResource(R.drawable.star_white32px);
                }
                MovieActivity.favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Movie movie = AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle());
                        movie.setFavorite(!movie.getFavorite());

                        AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle()));
                        AppDatabase.getAppDatabase(context).movieDao().insertAll(movie);

                        MainPresenter.updateListMovies();
                        if(movie.getFavorite()){
                            MovieActivity.favorite.setImageResource(R.drawable.star32px);
                        }else{
                            MovieActivity.favorite.setImageResource(R.drawable.star_white32px);
                        }
                    }
                });
            }
        }
    }
}
