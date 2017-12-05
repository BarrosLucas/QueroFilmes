package com.example.root.querofilmes.presenter;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.querofilmes.R;
import com.example.root.querofilmes.model.DAO.AppDatabase;
import com.example.root.querofilmes.model.DAO.Database;
import com.example.root.querofilmes.model.Movie;
import com.example.root.querofilmes.view.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by root on 28/11/17.
 */

public class MainPresenter {
    private static Context mainActivityContext;
    private static MainActivity mainActivity;

    public MainPresenter(Context context, MainActivity mainActivity){
        mainActivityContext = context;
        this.mainActivity = mainActivity;
    }

    public void populateCards(){
        int countMovies = Database.countMovie(AppDatabase.getAppDatabase(mainActivityContext));
        if(countMovies>0){
            List<Movie> movies = Database.getMovies(AppDatabase.getAppDatabase(mainActivityContext));
            for(int i = 1; i <= countMovies; i++){
                FrameLayout frameLayout = new FrameLayout(mainActivityContext);
                RelativeLayout relativeLayout = new RelativeLayout(mainActivityContext);
                ImageView imageView = new ImageView(mainActivityContext);
                TextView textView = new TextView(mainActivityContext);
                final int index = i;

                DisplayMetrics displayMetrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) mainActivityContext.getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);

                LinearLayout.LayoutParams paramFrameLayout = new LinearLayout.LayoutParams(Math.round(110*displayMetrics.density),Math.round(170*displayMetrics.density));
                frameLayout.setLayoutParams(paramFrameLayout);
                frameLayout.setBackgroundColor(0xFFFFFFFF);
                frameLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mainActivity,"Index: "+(index-1),Toast.LENGTH_SHORT).show();
                    }
                });
                //If the movie stay in medium column
                if((1%3)%2==0){
                    frameLayout.setPadding(Math.round(10*displayMetrics.density),0,Math.round(10*displayMetrics.density),0);
                }

                LinearLayout.LayoutParams paramRelativeLayoutImageView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                relativeLayout.setLayoutParams(paramRelativeLayoutImageView);

                imageView.setLayoutParams(paramRelativeLayoutImageView);
                Picasso.with(mainActivityContext).load(movies.get(i-1).getPoster()).into(imageView);

                textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                textView.setTextColor(0xFFE0E0E0);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                textView.setText(movies.get(i-1).getTitle());

                relativeLayout.addView(imageView);
                relativeLayout.addView(textView);
                frameLayout.addView(relativeLayout);
                mainActivity.gridLayout.addView(frameLayout, new GridLayout.LayoutParams( GridLayout.spec((Integer)(i/3), GridLayout.CENTER), GridLayout.spec((i%3), GridLayout.CENTER)));


            }
        }
    }
}
