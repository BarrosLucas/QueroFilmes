package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.view.MainActivity;
import com.example.root.querofilmes.view.SearchMovie;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by root on 08/12/17.
 */

public class AdapterMovieMain {
    public static View generateList(final int position, View convertView, ViewGroup parent, final Context context, final List<Movie> movies){
        View view = new View(context);

            view = LayoutInflater.from(context).inflate(R.layout.movie_adapter, parent, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.folder_movie);
            Picasso.with(context).load(movies.get(position).getPoster()).into(imageView);

            TextView title = (TextView) view.findViewById(R.id.title_movie);
            title.setText(movies.get(position).getTitle());

            TextView year = (TextView) view.findViewById(R.id.year_movie);
            year.setText(movies.get(position).getYear());

            TextView abstractMovie = (TextView) view.findViewById(R.id.abstract_movie);
            abstractMovie.setText(movies.get(position).getPlot());

            final ImageView star = (ImageView) view.findViewById(R.id.star);
            if(movies.get(position).getFavorite()){
                star.setImageResource(R.drawable.star32px);
            }else{
                star.setImageResource(R.drawable.star_white32px);
            }
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie = AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(position).getTitle());
                    movie.setFavorite(!movie.getFavorite());

                    AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movies.get(position).getTitle()));
                    AppDatabase.getAppDatabase(context).movieDao().insertAll(movie);

                    try {
                        MainPresenter.updateListMovies();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(movie.getFavorite()){
                        star.setImageResource(R.drawable.star32px);
                    }else{
                        star.setImageResource(R.drawable.star_white32px);
                    }
                }
            });
        return view;
    }
}
