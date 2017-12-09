package com.example.root.querofilmes.view;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.root.querofilmes.R;

/**
 * Created by root on 09/12/17.
 */

public class BaseActivity extends livroandroid.lib.activity.BaseActivity{
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }
}
