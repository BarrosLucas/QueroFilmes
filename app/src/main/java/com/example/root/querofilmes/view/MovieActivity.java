package com.example.root.querofilmes.view;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.querofilmes.InitialScreem;
import com.example.root.querofilmes.R;
import com.example.root.querofilmes.presenter.MoviePresenter;

public class MovieActivity extends AppCompatActivity {

    public static TextView year,genre,language,production,plot,title;
    public static ImageView picture,favorite;
    public static Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        title = (TextView) findViewById(R.id.movie_title);
        year = (TextView) findViewById(R.id.movie_year);
        genre = (TextView) findViewById(R.id.movie_genger);
        language = (TextView) findViewById(R.id.movie_language);
        production = (TextView) findViewById(R.id.movie_production);
        plot = (TextView) findViewById(R.id.plot);

        picture = (ImageView) findViewById(R.id.movie_picture);
        favorite = (ImageView) findViewById(R.id.favorite);

        button = (Button) findViewById(R.id.remove);


        MoviePresenter moviePresenter = new MoviePresenter(this, InitialScreem.index, this);
    }

    @Override
    public void onBackPressed() {
        MainActivity.index = 0;
        finish();
    }

    public void end(){
        MainActivity.index = 0;
        finish();
    }
}
