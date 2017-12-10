package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Movie;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.model.service.MovieResponse;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.root.querofilmes.model.service.MovieInterface.charset;

/**
 * Created by root on 05/12/17.
 */

public class AdapterSearchPresenter {
    //Set the view element list
    public static View generateView(final int position, View convertView, ViewGroup parent, final Context context, final ListMovieResponse listMovieResponse) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_movie, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.movie);
        Picasso.with(context).load(listMovieResponse.Search.get(position).getPoster()).into(imageView);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(listMovieResponse.Search.get(position).getTitle());

        TextView year = (TextView) view.findViewById(R.id.year);
        year.setText("Ano: "+listMovieResponse.Search.get(position).getYear());

        //If the movie to be register in database of application, so button show "remover" and not "adicionar"
        final Button button = (Button) view.findViewById(R.id.add);
        if(AppDatabase.getAppDatabase(context).movieDao().findByTitle(listMovieResponse.Search.get(position).getTitle()) != null){
            button.setText("REMOVER");
        }else{
            button.setText("ADICIONAR");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Register the movie in database
                if(button.getText().toString().equalsIgnoreCase("adicionar")){
                String query = null;
                try {
                    query = String.format("?i=%s&apikey=%s",
                            URLEncoder.encode(listMovieResponse.Search.get(position).getImdbID(), charset),
                            URLEncoder.encode(MovieInterface.API_KEY, charset));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(MovieInterface.MOVIE_API_BASE_URL+query)
                        .build();

                Log.i("Query",query);

                final MovieInterface movieInterface = retrofit.create(MovieInterface.class);
                Observable<MovieResponse> observable = movieInterface.search();

                observable
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MovieResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,"Falha na operação",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        if(AppDatabase.getAppDatabase(context).movieDao().findByTitle(movieResponse.getTitle()) == null) {
                            AppDatabase.getAppDatabase(context).movieDao().insertAll(new Movie(movieResponse.getTitle(), movieResponse.getYear(), movieResponse.getReleased(), movieResponse.getRuntime(), movieResponse.getGenre(), movieResponse.getDirector(), movieResponse.getPlot(), movieResponse.getLanguage(), movieResponse.getPoster(), movieResponse.getProduction(),false));
                            Toast.makeText(context,"Filme cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
                        }
                        button.setText("REMOVER");

                        try {
                            MainPresenter.updateListMovies();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else{
                    //Delete the movie of database
                    AppDatabase.getAppDatabase(context).movieDao().delete(AppDatabase.getAppDatabase(context).movieDao().findByTitle(listMovieResponse.Search.get(position).getTitle()));


                    try {
                        MainPresenter.updateListMovies();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    button.setText("ADICIONAR");
                    Toast.makeText(context,"Filme deletado com sucesso!",Toast.LENGTH_SHORT).show();
                }

        }});

        return view;


    }
}
