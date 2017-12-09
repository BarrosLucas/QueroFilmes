package com.example.root.querofilmes.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.presenter.MainPresenter;

import java.util.List;

import livroandroid.lib.fragment.NavigationDrawerFragment;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private MenuItemAdapter options;

    private static MainPresenter mainPresenter;
    private static SearchView.OnQueryTextListener listener;
    private NavigationDrawerFragment navigationDrawerFragment;
    public static ListView listView;
    public static int index = 0;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_copy);

        setUpToolbar();

        //navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);

        //DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //navigationDrawerFragment.setUp(drawerLayout);


        listView = (ListView) findViewById(R.id.movie);
        listView.setOnItemClickListener(this);

        context = this;




        //mainPresenter = new MainPresenter(getBaseContext(),this);
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



    public void registerMovie(View v){
        /*//Toast.makeText(this,"Cadastrar novo filme",Toast.LENGTH_SHORT);
        Intent intent = new Intent(this,SearchMovie.class);
        startActivity(intent);*/
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Toast.makeText(this,"Indice: "+position,Toast.LENGTH_SHORT).show();
        index = position;
        Intent intent = new Intent(this,MovieActivity.class);
        startActivity(intent);*/
    }
}
