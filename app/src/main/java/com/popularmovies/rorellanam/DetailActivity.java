package com.popularmovies.rorellanam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Toolbar
        Toolbar detailToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(detailToolbar);

        detailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.finish();
            }
        });

        Intent intent = this.getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mMovie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);
        }

        //Sets value to UI

        textView = (TextView) findViewById(R.id.movie_title);
        textView.setText(mMovie.getTitle());

        textView = (TextView) findViewById(R.id.sinopsis);
        textView.setText(mMovie.getSipnosis());

        textView = (TextView) findViewById(R.id.date);
        textView.setText(mMovie.getDate());

        ImageView imageView = (ImageView) findViewById(R.id.detail_image);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + mMovie.getImagePoster()).into(imageView);
    }
}
