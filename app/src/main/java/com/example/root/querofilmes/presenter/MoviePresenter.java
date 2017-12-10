package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Movie;
import com.example.root.querofilmes.view.activity.MovieActivity;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by root on 08/12/17.
 */

public class MoviePresenter {
    public static Context context;

    //Set the values view to receiver the values movie
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

                //Delete the movie on click button and set activity
                MovieActivity.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle()));
                        Toast.makeText(context,"Filme deletado!",Toast.LENGTH_SHORT).show();
                        try {
                            MainPresenter.updateListMovies();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        movieActivity.end();
                    }


                });

                //Set the star conform the favorite movie
                if(movies.get(index).getFavorite()){
                    MovieActivity.favorite.setImageResource(R.drawable.star32px);
                }else{
                    MovieActivity.favorite.setImageResource(R.drawable.star_white32px);
                }

                //Set the favorite in click star
                MovieActivity.favorite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Movie movie = AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle());
                        movie.setFavorite(!movie.getFavorite());

                        AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(index).getTitle()));
                        AppDatabase.getAppDatabase(context).movieDao().insertAll(movie);

                        try {
                            MainPresenter.updateListMovies();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
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
