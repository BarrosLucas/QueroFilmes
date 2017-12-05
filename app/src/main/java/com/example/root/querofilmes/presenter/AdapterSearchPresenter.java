package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieForList;
import com.example.root.querofilmes.model.service.MovieInterface;
import com.example.root.querofilmes.model.service.MovieResponse;
import com.example.root.querofilmes.view.AdapterMovieView;
import com.example.root.querofilmes.view.MainActivity;
import com.example.root.querofilmes.view.SearchMovie;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.root.querofilmes.model.service.MovieInterface.charset;

/**
 * Created by root on 05/12/17.
 */

public class AdapterSearchPresenter {

    public static View generateView(final int position, View convertView, ViewGroup parent, Context context, final ListMovieResponse listMovieResponse) {
        View view = LayoutInflater.from(SearchMovie.context).inflate(R.layout.adapter_view_movie, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.movie);
        Picasso.with(context).load(listMovieResponse.Search.get(position).getPoster()).into(imageView);

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(listMovieResponse.Search.get(position).getTitle());

        TextView year = (TextView) view.findViewById(R.id.year);
        year.setText("Ano: "+listMovieResponse.Search.get(position).getYear());

        final Button button = (Button) view.findViewById(R.id.add);
        if(AppDatabase.getAppDatabase(SearchMovie.context).movieDao().findByTitle(listMovieResponse.Search.get(position).getTitle()) != null){
            button.setText("REMOVER");
        }else{
            button.setText("ADICIONAR");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .baseUrl(MovieInterface.MOVIE_API_BASE_URL+query)
                        .build();

                Log.i("Query",query);

                final MovieInterface movieInterface = retrofit.create(MovieInterface.class);
                Call<MovieResponse> callMovie = movieInterface.search();
                callMovie.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if(response.isSuccessful()) {
                            if(response.body() != null){
                                MovieResponse movieResponse = response.body();

                                if(AppDatabase.getAppDatabase(SearchMovie.context).movieDao().findByTitle(movieResponse.getTitle()) == null) {
                                    AppDatabase.getAppDatabase(SearchMovie.context).movieDao().insertAll(new Movie(movieResponse.getTitle(), movieResponse.getYear(), movieResponse.getReleased(), movieResponse.getRuntime(), movieResponse.getGenre(), movieResponse.getDirector(), movieResponse.getPlot(), movieResponse.getLanguage(), movieResponse.getPoster(), movieResponse.getProduction()));
                                    button.setText("REMOVER");
                                }
                                Toast.makeText(SearchMovie.context,"Qnt. "+AppDatabase.getAppDatabase(SearchMovie.context).movieDao().countMovie(),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SearchMovie.context,"Error",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            ResponseBody responseBody = response.errorBody();
                            try {
                                Log.i("Erro",responseBody.source().readUtf8());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(SearchMovie.context,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(SearchMovie.context,"Falha",Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
            } else{
                    AppDatabase.getAppDatabase(SearchMovie.context).movieDao().delete(AppDatabase.getAppDatabase(SearchMovie.context).movieDao().findByTitle(listMovieResponse.Search.get(position).getTitle()));
                    button.setText("ADICIONAR");
                    Toast.makeText(SearchMovie.context,"Qnt. "+AppDatabase.getAppDatabase(SearchMovie.context).movieDao().countMovie(),Toast.LENGTH_SHORT).show();
                }

        }});

        return view;


    }
}
