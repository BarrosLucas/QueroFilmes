package com.example.root.querofilmes.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.service.ListMovieResponse;
import com.example.root.querofilmes.model.service.MovieForList;
import com.example.root.querofilmes.presenter.AdapterSearchPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 05/12/17.
 */

public class AdapterMovieView extends BaseAdapter{
    private Context context;
    private ListMovieResponse listMovieResponse;

    public AdapterMovieView(Context context, ListMovieResponse listMovieResponse){
        this.context = context;
        this.listMovieResponse = listMovieResponse;
    }

    @Override
    public int getCount() {
        if(listMovieResponse.Search != null) {
            return listMovieResponse.Search.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listMovieResponse.Search.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return AdapterSearchPresenter.generateView(position,convertView,parent, context, listMovieResponse);
    }
}
