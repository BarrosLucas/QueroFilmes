package com.example.root.querofilmes.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.root.querofilmes.model.DAO.MovieDAO;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.presenter.AdapterMovieMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 08/12/17.
 */

public class AdapterMovieMainView extends BaseAdapter {
    private Context context;
    private List<Movie> movies;

    public AdapterMovieMainView(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return AdapterMovieMain.generateList(position,convertView,parent,context,movies);
    }
}
