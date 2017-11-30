package com.example.root.querofilmes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.presenter.SearchMoviePresenter;

import java.io.UnsupportedEncodingException;

public class SearchMovie extends AppCompatActivity {
    EditText editText;
    private static SearchMoviePresenter searchMoviePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        searchMoviePresenter = new SearchMoviePresenter(getBaseContext(),this);

        editText = (EditText) findViewById(R.id.textSearch);
    }
    public void search(View view){
        try {
            searchMoviePresenter.searchMovieOnOMDB(editText.getText().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
