package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.support.v7.widget.SearchView;

import com.example.root.querofilmes.view.MainActivity;

/**
 * Created by root on 28/11/17.
 */

public class MainController {
    private static Context mainActivityContext;
    private static MainActivity mainActivity;

    public MainController(Context context, MainActivity mainActivity){
        mainActivityContext = context;
        mainActivity = mainActivity;
    }

    public SearchView.OnQueryTextListener onSearch(){
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        };
    }

    public void populateCards(){

    }
}
