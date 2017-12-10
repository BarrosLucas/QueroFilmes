package com.example.root.querofilmes.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.presenter.MainPresenter;

import java.io.UnsupportedEncodingException;

public class InitialScreem extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    private static MainPresenter mainPresenter;
    private static SearchView.OnQueryTextListener listener;
    public static ListView listView;
    public static ListView searchListView;
    public static int index = 0;
    public static Context context;
    public static boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screem);

        //Configure toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Configure Drawaer layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        listView = (ListView) findViewById(R.id.movie);
        listView.setOnItemClickListener(this);

        searchListView = (ListView) findViewById(R.id.list_item);
        searchListView.setOnItemClickListener(this);



        context = this;

        mainPresenter = new MainPresenter(getBaseContext(),this);
        try {
            mainPresenter.populateCards();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Action in search from movies in API
        listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    mainPresenter.searchMovieOnOMDB(query);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(isSearch){
            try {
                mainPresenter.setIsSearch();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }
    }

    //Configure the menu with search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);



        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);


        if(searchView == null){
            Log.i("Search","null");
        }else {
            Log.i("Search","not null");
            MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    isSearch = true;
                    try {
                        mainPresenter.setIsSearch();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
            });
            searchView.setOnQueryTextListener(listener);
        }
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Click in show all in Drawer
        if (id == R.id.nav_all) {
            try {
                mainPresenter.populateCards();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //Click in favorite in Drawer
        else if (id == R.id.nav_favorite) {
            try {
                mainPresenter.populateFavoritesMovies();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Click in list element
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        index = position;
        Intent intent = new Intent(this,MovieActivity.class);
        startActivity(intent);
    }
}
