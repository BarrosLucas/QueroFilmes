package com.example.root.querofilmes.view;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.querofilmes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 09/12/17.
 */

public class NavDrawerMenuItem {
    public int title;
    public int imageView;
    public boolean selected;

    public NavDrawerMenuItem(int title, int imageView){
        this.title = title;
        this.imageView = imageView;
    }

    public static List<NavDrawerMenuItem> getList(){
        List<NavDrawerMenuItem> options = new ArrayList<>();
        options.add(new NavDrawerMenuItem(R.string.all,R.drawable.world32px));
        options.add(new NavDrawerMenuItem(R.string.favorite,R.drawable.star32px));
        options.add(new NavDrawerMenuItem(R.string.category,R.drawable.category32px));
        return options;
    }

}
