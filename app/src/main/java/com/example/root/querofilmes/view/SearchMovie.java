package com.example.root.querofilmes.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.service.MovieForList;
import com.example.root.querofilmes.presenter.SearchMoviePresenter;

import java.io.UnsupportedEncodingException;

public class SearchMovie extends AppCompatActivity implements AdapterView.OnItemClickListener{
    EditText editText;
    public ListView listView;
    public static Context context;
    private static SearchMoviePresenter searchMoviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        searchMoviePresenter = new SearchMoviePresenter(getBaseContext(),this);

        editText = (EditText) findViewById(R.id.textSearch);
        listView = (ListView) findViewById(R.id.list_item);
        listView.setOnItemClickListener(this);
    }
    public void search(View view){
        try {
            searchMoviePresenter.searchMovieOnOMDB(editText.getText().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,searchMoviePresenter.getIdOmdbMovie(position),Toast.LENGTH_SHORT).show();
    }
}
