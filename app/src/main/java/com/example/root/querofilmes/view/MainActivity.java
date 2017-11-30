package com.example.root.querofilmes.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private static MainPresenter mainPresenter;
    public static GridLayout gridLayout;
    private static SearchView.OnQueryTextListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout) findViewById(R.id.grid);

        mainPresenter = new MainPresenter(getBaseContext(),this);
        mainPresenter.populateCards();

        listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // newText is text entered by user to SearchView

                return false;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        if(searchView == null){
            Log.i("Search","null");
        }else {
            Log.i("Search","not null");
            searchView.setOnQueryTextListener(listener);
        }
        return true;
    }

    public void registerMovie(View view){
        Toast.makeText(this,"Cadastrar novo filme",Toast.LENGTH_SHORT);
        Intent intent = new Intent(this,SearchMovie.class);
        startActivity(intent);
    }


}
